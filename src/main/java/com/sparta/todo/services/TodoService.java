package com.sparta.todo.services;


import com.sparta.todo.dtos.TodoDTO;
import com.sparta.todo.dtos.TodoMapper;
import com.sparta.todo.entities.Todo;
import com.sparta.todo.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final TodoRepository repository;
    private final TodoMapper mapper;

    @Autowired
    public TodoService(TodoRepository repository, TodoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<TodoDTO> getTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public TodoDTO getTodoById(int id) {
        Todo todo = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo ID: " + id));
        return mapper.toDto(todo);
    }

    public TodoDTO saveTodo(TodoDTO todoDto){
        Todo todo = mapper.toEntity(todoDto);
        Todo saveTodo = repository.save(todo);
        return mapper.toDto(saveTodo);
    }

    public void deleteTodoById(int id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Todo not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public void update(int id, TodoDTO updatedTodoDto){
        Todo existingTodo = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid todo ID: " + id));

        existingTodo.setTitle(updatedTodoDto.getTitle());
        existingTodo.setDescription(updatedTodoDto.getDescription());
        existingTodo.setComplete(updatedTodoDto.isComplete());


        saveTodo(mapper.toDto(existingTodo));
    }

    public List<TodoDTO> filterByTitle_and_description(String title, String description){
        List<Todo> filteredTodos = repository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(title, description);
        return filteredTodos.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TodoDTO> getTodosForSpartan(String username) {
        return repository.findAll()
                .stream()
                .filter(x -> x.getSpartan().getUsername().equals(username))
                .map(mapper::toDto)
                .toList();
    }

    public boolean hasRole(String role, Authentication authentication){
        return authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(role));
    }
}