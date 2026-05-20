package com.sparta.todo.dtos;

import com.sparta.todo.entities.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses={SpartanMapper.class})
public interface TodoMapper {
    @Mapping(source = "spartan", target = "spartanDto")
    TodoDTO toDto(Todo todo);

    @Mapping(source = "spartanDto", target = "spartan")
    Todo toEntity(TodoDTO todoDTO);
}