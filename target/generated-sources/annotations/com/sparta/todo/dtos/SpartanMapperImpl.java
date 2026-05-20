package com.sparta.todo.dtos;

import com.sparta.todo.entities.Spartan;
import com.sparta.todo.entities.Todo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-20T12:13:59+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class SpartanMapperImpl implements SpartanMapper {

    @Override
    public SpartanDTO toDTO(Spartan spartan) {
        if ( spartan == null ) {
            return null;
        }

        SpartanDTO spartanDTO = new SpartanDTO();

        if ( spartan.getId() != null ) {
            spartanDTO.setId( spartan.getId() );
        }
        spartanDTO.setUsername( spartan.getUsername() );
        spartanDTO.setRole( spartan.getRole() );
        spartanDTO.setTodoItems( todoListToTodoDTOList( spartan.getTodoItems() ) );

        return spartanDTO;
    }

    @Override
    public Spartan toEntity(SpartanDTO spartanDTO) {
        if ( spartanDTO == null ) {
            return null;
        }

        Spartan spartan = new Spartan();

        spartan.setId( spartanDTO.getId() );
        spartan.setRole( spartanDTO.getRole() );

        return spartan;
    }

    protected TodoDTO todoToTodoDTO(Todo todo) {
        if ( todo == null ) {
            return null;
        }

        TodoDTO todoDTO = new TodoDTO();

        todoDTO.setTitle( todo.getTitle() );
        todoDTO.setDescription( todo.getDescription() );
        todoDTO.setComplete( todo.isComplete() );
        todoDTO.setId( todo.getId() );

        return todoDTO;
    }

    protected List<TodoDTO> todoListToTodoDTOList(List<Todo> list) {
        if ( list == null ) {
            return null;
        }

        List<TodoDTO> list1 = new ArrayList<TodoDTO>( list.size() );
        for ( Todo todo : list ) {
            list1.add( todoToTodoDTO( todo ) );
        }

        return list1;
    }
}
