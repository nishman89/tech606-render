package com.sparta.todo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(max = 50)
    private String title;

    private String description;

    private boolean complete;

    @PastOrPresent
    @Column(name = "date_created")
    private LocalDate dateCreated = LocalDate.now();

    @ManyToOne
    @JoinColumn(name="spartan_id")
    private  Spartan spartan;


    // Constructors

    public Todo(String title, String description, boolean complete, LocalDate dateCreated, Spartan spartan) {
        this(title, description, complete, dateCreated);
        this.spartan = spartan;
    }
    public Todo(String title, String description, boolean complete) {
        this.title = title;
        this.description = description;
        this.complete = complete;
    }

    public Todo(String title, String description, boolean complete, LocalDate dateCreated) {
        this(title, description, complete);
        this.dateCreated = dateCreated;
    }

    public Spartan getSpartan() {
        return spartan;
    }

    public void setSpartan(Spartan spartan) {
        this.spartan = spartan;
    }

    public Todo() {

    }


    // Getters and setters (or use Lombok @Data)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }
    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", complete=" + complete +
                ", dateCreated=" + dateCreated +
                '}';
    }

}