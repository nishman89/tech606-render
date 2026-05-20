package com.sparta.todo.controllers;

import com.sparta.todo.dtos.TodoDTO;
import com.sparta.todo.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/todos") // Specifies all endpoints for this controller start with /todos
public class TodoController {

    private final TodoService todoService;

    // Constructor injection for TodoService
    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @GetMapping({"","/"}) // Maps to /todos
    public String listTodos(Model model, Authentication authentication) {
        String userName = authentication.getName();
        List<TodoDTO> todos = todoService.hasRole("ROLE_TRAINER", authentication) ?
                todoService.getTodos() : todoService.getTodosForSpartan(userName);
        model.addAttribute("todos", todos);
        return "todos/index";
    }

    @GetMapping("/{id}")
    public String viewTodo(@PathVariable int id, Model model) {
        // getTodoById throws IllegalArgumentException if not found - no Optional needed here
        TodoDTO todo = todoService.getTodoById(id);
        model.addAttribute("todo", todo);
        return "todos/view";
    }

    @PostMapping("/{id}/update")
    public String updateTodo(@PathVariable int id, @ModelAttribute TodoDTO updatedTodo) {
        // The service handles fetching, updating, and saving - the controller just delegates
        todoService.update(id, updatedTodo);
        return "redirect:/todos"; // Redirects to the /todos page
    }

    @PostMapping("/{id}/delete")
    public String deleteTodo(@PathVariable int id) {
        todoService.deleteTodoById(id);
        return "redirect:/todos";
    }

    @GetMapping("/new")
    public String newTodoForm(Model model) {
        model.addAttribute("todo", new TodoDTO());
        return "todos/new";
    }

    @PostMapping("/save")
    public String saveTodo(@ModelAttribute TodoDTO newTodo) {
        todoService.saveTodo(newTodo);
        return "redirect:/todos";
    }

    @GetMapping("/search")
    public String searchTodos(@RequestParam("query") String query, Model model) {
        // Search for todos by title or description
        List<TodoDTO> searchResults = todoService.filterByTitle_and_description(query, query);
        model.addAttribute("todos", searchResults);
        return "todos/index"; // Return the same index.html template with filtered results
    }
}