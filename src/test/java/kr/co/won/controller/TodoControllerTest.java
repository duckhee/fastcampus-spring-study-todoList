package kr.co.won.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.won.model.TodoEntity;
import kr.co.won.model.TodoRequest;
import kr.co.won.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TodoController.class)
@ExtendWith(MockitoExtension.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService service;

    private TodoEntity expected;

    @BeforeEach
    void setup() {
        this.expected = new TodoEntity();
        this.expected.setId(1L);
        this.expected.setTitle("TEST TITLE");
        this.expected.setOrder(0L);
        this.expected.setCompleted(false);
    }

    @Test
    @DisplayName(value = "01. create controller test")
    void create() throws Exception {
        when(service.add(any(TodoRequest.class)))
                .then((i) -> {
                    TodoRequest request = i.getArgument(0, TodoRequest.class);
                    return new TodoEntity(this.expected.getId(), request.getTitle(), this.expected.getOrder(), this.expected.getCompleted());
                });

        TodoRequest request = new TodoRequest();
        request.setTitle("ANY TITLE");

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andDo(print());
    }
}