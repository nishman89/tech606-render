package com.sparta.todo.dtos;


import com.sparta.todo.entities.Spartan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpartanMapper {
    SpartanDTO toDTO(Spartan spartan);
    Spartan toEntity(SpartanDTO spartanDTO);
}
