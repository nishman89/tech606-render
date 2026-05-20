package com.sparta.todo.repositories;

import com.sparta.todo.entities.Spartan;
import com.sparta.todo.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);


}
