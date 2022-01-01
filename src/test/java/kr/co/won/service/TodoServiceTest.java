package kr.co.won.service;

import kr.co.won.model.TodoEntity;
import kr.co.won.model.TodoRequest;
import kr.co.won.repository.TodoRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService service;


    @Test
    @DisplayName(value = "01. todo service create test")
    void add() {
        when(todoRepository.save(any(TodoEntity.class)))
                .then(AdditionalAnswers.returnsFirstArg());
        TodoRequest expected = new TodoRequest();
        expected.setTitle("Test Title");

        TodoEntity actual = service.add(expected);

        assertEquals(expected.getTitle(), actual.getTitle());
    }

    @Test
    @DisplayName(value = "02. todo service search test")
    void searchById() {
        TodoEntity entity = new TodoEntity();
        entity.setId(1L);
        entity.setTitle("TITLE");
        entity.setOrder(0L);
        entity.setCompleted(false);

        Optional<TodoEntity> optional = Optional.of(entity);

        given(todoRepository.findById(anyLong()))
                .willReturn(optional);

        TodoEntity actual = service.searchById(1L);
        TodoEntity expected = optional.get();
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getOrder(), actual.getOrder());
        assertEquals(expected.getCompleted(), actual.getCompleted());
    }

    @Test
    @DisplayName(value = "03. search By id Failed Test")
    void searchByIdFailed() {
        given(todoRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            service.searchById(1L);
        });
    }

}