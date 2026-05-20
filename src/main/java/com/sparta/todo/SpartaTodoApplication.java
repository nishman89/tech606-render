package com.sparta.todo;

import com.sparta.todo.entities.Spartan;
import com.sparta.todo.entities.Todo;
import com.sparta.todo.repositories.SpartanRepository;
import com.sparta.todo.repositories.TodoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class SpartaTodoApplication {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(SpartaTodoApplication.class, args);
        TodoRepository todoRepository = context.getBean(TodoRepository.class);
        for(Todo todo : todoRepository.findAll()){
            System.out.println(todo);
        }

        SpartanRepository spartanRepository = context.getBean(SpartanRepository.class);
        List<Spartan> spartans = spartanRepository.findAllWithTodos();
        for(Spartan spartan : spartans){
            System.out.println(spartan);
            for(var todo : spartan.getTodoItems()){
                System.out.println(todo);
            }
        }

    }

}
