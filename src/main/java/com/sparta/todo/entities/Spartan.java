package com.sparta.todo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Spartan implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String role; // Trainer or Spartan

    @OneToMany(mappedBy = "spartan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Todo> todoItems = new ArrayList<>();

    public Spartan(String username, String password, List<Todo> toDoItems) {
        this(username,password);
        this.todoItems = toDoItems;
    }
    public Spartan(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Spartan(String username, String password, String role, List<Todo> todoItems) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.todoItems = todoItems;
    }

    public Spartan() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void addTodoItem(Todo todo) {
        todoItems.add(todo);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Todo> getTodoItems() {
        return todoItems;
    }

    public void setTodoItems(List<Todo> todoItems) {
        this.todoItems = todoItems;
    }

    @Override
    public String toString() {
        return "Spartan{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
