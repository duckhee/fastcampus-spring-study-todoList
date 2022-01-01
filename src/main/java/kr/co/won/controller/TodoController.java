package kr.co.won.controller;

import kr.co.won.model.TodoEntity;
import kr.co.won.model.TodoRequest;
import kr.co.won.model.TodoResponse;
import kr.co.won.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
// cross domain 를 막기 위한 annotation¬
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/")
public class TodoController {

    private final TodoService service;

    @PostMapping
    public ResponseEntity<TodoResponse> create(@RequestBody TodoRequest request) {
        if (ObjectUtils.isEmpty(request.getTitle())) {
            return ResponseEntity.badRequest().build();
        }
        if (ObjectUtils.isEmpty(request.getOrder())) {
            request.setOrder(0L);
        }
        if (ObjectUtils.isEmpty(request.getCompleted())) {
            request.setCompleted(false);
        }
        TodoEntity result = service.add(request);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponse>> readAll() {
        List<TodoEntity> results = service.searchAll();
        List<TodoResponse> response = results.stream().map(TodoResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TodoResponse> readOne(@PathVariable(name = "id") Long id) {
        TodoEntity result = service.searchById(id);
        return ResponseEntity.ok(new TodoResponse(result));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<TodoResponse> update(@PathVariable(name = "id") Long id, @RequestBody TodoRequest request) {
        TodoEntity updateEntity = service.updateById(id, request);

        return ResponseEntity.ok(new TodoResponse(updateEntity));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable(name = "id") Long id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        service.deleteAll();
        return ResponseEntity.ok().build();
    }

}
