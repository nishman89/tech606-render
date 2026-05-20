package com.sparta.todo.dtos;

import com.sparta.todo.entities.Todo;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-20T12:13:59+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class TodoMapperImpl implements TodoMapper {

    @Autowired
    private SpartanMapper spartanMapper;

    @Override
    public TodoDTO toDto(Todo todo) {
        if ( todo == null ) {
            return null;
        }

        TodoDTO todoDTO = new TodoDTO();

        todoDTO.setSpartanDto( spartanMapper.toDTO( todo.getSpartan() ) );
        todoDTO.setTitle( todo.getTitle() );
        todoDTO.setDescription( todo.getDescription() );
        todoDTO.setComplete( todo.isComplete() );
        todoDTO.setId( todo.getId() );

        return todoDTO;
    }

    @Override
    public Todo toEntity(TodoDTO todoDTO) {
        if ( todoDTO == null ) {
            return null;
        }

        Todo todo = new Todo();

        todo.setSpartan( spartanMapper.toEntity( todoDTO.getSpartanDto() ) );
        todo.setId( todoDTO.getId() );
        todo.setTitle( todoDTO.getTitle() );
        todo.setDescription( todoDTO.getDescription() );
        todo.setComplete( todoDTO.isComplete() );

        return todo;
    }
}
