package com.sparta.todo;

import com.sparta.todo.dtos.TodoDTO;
import com.sparta.todo.dtos.TodoMapper;
import com.sparta.todo.entities.Todo;
import com.sparta.todo.repositories.TodoRepository;
import com.sparta.todo.services.TodoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class TodoServiceTests {

    private final TodoRepository repository = Mockito.mock(TodoRepository.class);
    private final TodoMapper mapper = Mockito.mock(TodoMapper.class);
    private final TodoService sut = new TodoService(repository, mapper);

    @Test
    @DisplayName("Given dependencies, when constructing TodoService, then service is created")
    void constructServiceTest() {
        // Given / When
        TodoService service = new TodoService(repository, mapper);

        // Then
        Assertions.assertNotNull(service);
        Assertions.assertInstanceOf(TodoService.class, service);
    }

    @Test
    @DisplayName("Given todos exist, when getTodos is called, then returns mapped DTO list")
    void getTodos_happyPath() {
        // Given
        Todo todo1 = new Todo("Task 1", "Desc 1", false, LocalDate.now());
        Todo todo2 = new Todo("Task 2", "Desc 2", true, LocalDate.now());
        List<Todo> todos = List.of(todo1, todo2);

        TodoDTO dto1 = new TodoDTO();
        dto1.setTitle("Task 1");
        dto1.setDescription("Desc 1");
        dto1.setComplete(false);

        TodoDTO dto2 = new TodoDTO();
        dto2.setTitle("Task 2");
        dto2.setDescription("Desc 2");
        dto2.setComplete(true);

        when(repository.findAll()).thenReturn(todos);
        when(mapper.toDto(todo1)).thenReturn(dto1);
        when(mapper.toDto(todo2)).thenReturn(dto2);

        // When
        List<TodoDTO> result = sut.getTodos();

        // Then
        Assertions.assertEquals(2, result.size());
        Assertions.assertSame(dto1, result.get(0));
        Assertions.assertSame(dto2, result.get(1));

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDto(todo1);
        verify(mapper, times(1)).toDto(todo2);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    @DisplayName("Given todo exists, when getTodoById is called, then returns mapped DTO")
    void getTodoById_happyPath() {
        // Given
        int id = 1;
        Todo todo = new Todo("Task 1", "Desc 1", true, LocalDate.now());

        TodoDTO dto = new TodoDTO();
        dto.setId(id);
        dto.setTitle("Task 1");
        dto.setDescription("Desc 1");
        dto.setComplete(true);

        when(repository.findById(id)).thenReturn(Optional.of(todo));
        when(mapper.toDto(todo)).thenReturn(dto);

        // When
        TodoDTO result = sut.getTodoById(id);

        // Then
        Assertions.assertSame(dto, result);

        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(todo);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    @DisplayName("Given todo does not exist, when getTodoById is called, then throws IllegalArgumentException")
    void getTodoById_sadPath_notFound() {
        // Given
        int id = 999;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        IllegalArgumentException ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sut.getTodoById(id)
        );
        Assertions.assertEquals("Invalid todo ID: " + id, ex.getMessage());

        verify(repository, times(1)).findById(id);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    @DisplayName("Given todo exists, when deleteTodoById is called, then checks existence and deletes")
    void deleteTodoById_happyPath() {
        // Given
        int id = 1;
        when(repository.existsById(id)).thenReturn(true);

        // When
        sut.deleteTodoById(id);

        // Then
        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).deleteById(id);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    @DisplayName("Given todo does not exist, when deleteTodoById is called, then throws and does not delete")
    void deleteTodoById_sadPath_notFound() {
        // Given
        int id = 123;
        when(repository.existsById(id)).thenReturn(false);

        // When / Then
        IllegalArgumentException ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sut.deleteTodoById(id)
        );
        Assertions.assertEquals("Todo not found with id: " + id, ex.getMessage());

        verify(repository, times(1)).existsById(id);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    @DisplayName("Given query matches, when filterByTitle_and_description is called, then returns mapped DTOs")
    void filterByTitleAndDescription_happyPath() {
        // Given
        String query = "Task";

        Todo todo1 = new Todo("Task 1", "Desc 1", false, LocalDate.now());
        Todo todo2 = new Todo("Task 2", "Desc 2", true, LocalDate.now());

        TodoDTO dto1 = new TodoDTO();
        dto1.setTitle("Task 1");
        TodoDTO dto2 = new TodoDTO();
        dto2.setTitle("Task 2");

        when(repository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query))
                .thenReturn(List.of(todo1, todo2));
        when(mapper.toDto(todo1)).thenReturn(dto1);
        when(mapper.toDto(todo2)).thenReturn(dto2);

        // When
        List<TodoDTO> result = sut.filterByTitle_and_description(query, query);

        // Then
        Assertions.assertEquals(2, result.size());
        Assertions.assertSame(dto1, result.get(0));
        Assertions.assertSame(dto2, result.get(1));

        verify(repository, times(1))
                .findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
        verify(mapper, times(1)).toDto(todo1);
        verify(mapper, times(1)).toDto(todo2);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    @DisplayName("Given todo exists and updated DTO, when update is called, then entity is updated and saved")
    void update_happyPath() {
        // Given
        int id = 7;

        Todo existing = new Todo("Old Title", "Old Desc", false, LocalDate.now());
        existing.setId(id);

        TodoDTO updatedDto = new TodoDTO();
        updatedDto.setTitle("New Title");
        updatedDto.setDescription("New Desc");
        updatedDto.setComplete(true);

        TodoDTO dtoFromExistingAfterMutation = new TodoDTO();
        dtoFromExistingAfterMutation.setId(id);
        dtoFromExistingAfterMutation.setTitle("New Title");
        dtoFromExistingAfterMutation.setDescription("New Desc");
        dtoFromExistingAfterMutation.setComplete(true);

        Todo entityToSave = new Todo("New Title", "New Desc", true, LocalDate.now());
        Todo savedEntity = new Todo("New Title", "New Desc", true, LocalDate.now());

        TodoDTO savedDto = new TodoDTO();
        savedDto.setId(id);
        savedDto.setTitle("New Title");
        savedDto.setDescription("New Desc");
        savedDto.setComplete(true);

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(mapper.toDto(existing)).thenReturn(dtoFromExistingAfterMutation);
        when(mapper.toEntity(dtoFromExistingAfterMutation)).thenReturn(entityToSave);
        when(repository.save(entityToSave)).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(savedDto);

        // When
        sut.update(id, updatedDto);

        // Then (entity mutated)
        Assertions.assertEquals("New Title", existing.getTitle());
        Assertions.assertEquals("New Desc", existing.getDescription());
        Assertions.assertTrue(existing.isComplete());

        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDto(existing);
        verify(mapper, times(1)).toEntity(dtoFromExistingAfterMutation);
        verify(repository, times(1)).save(entityToSave);
        verify(mapper, times(1)).toDto(savedEntity);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    @DisplayName("Given todo does not exist, when update is called, then throws and does not save")
    void update_sadPath_notFound() {
        // Given
        int id = 404;

        TodoDTO updatedDto = new TodoDTO();
        updatedDto.setTitle("New");
        updatedDto.setDescription("New");
        updatedDto.setComplete(true);

        when(repository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        IllegalArgumentException ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> sut.update(id, updatedDto)
        );
        Assertions.assertEquals("Invalid todo ID: " + id, ex.getMessage());

        verify(repository, times(1)).findById(id);
        verifyNoMoreInteractions(repository, mapper);
    }

}
