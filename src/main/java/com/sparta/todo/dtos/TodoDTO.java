package com.sparta.todo.dtos;

public class TodoDTO {


    private  int id;
    private String title;
    private String description;

    private boolean complete;

    private SpartanDTO spartanDto;

    public TodoDTO(String title, String description, boolean complete, SpartanDTO spartanDto) {
        this(title, description, complete);
        this.spartanDto = spartanDto;
    }

    public TodoDTO(String title, String description, boolean complete) {
        this.title = title;
        this.description = description;
        this.complete = complete;
    }

    public TodoDTO() {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SpartanDTO getSpartanDto() {
        return spartanDto;
    }

    public void setSpartanDto(SpartanDTO spartanDto) {
        this.spartanDto = spartanDto;
    }
}