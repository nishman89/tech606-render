package com.sparta.todo.repositories;

import com.sparta.todo.entities.Spartan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpartanRepository extends JpaRepository<Spartan, Integer> {
    @Query("SELECT s FROM Spartan s WHERE s.username = :username")
    Spartan findbyUserName(@Param("username") String username);

    @Query("SELECT s FROM Spartan s LEFT JOIN FETCH s.todoItems")
    List<Spartan> findAllWithTodos();
}
