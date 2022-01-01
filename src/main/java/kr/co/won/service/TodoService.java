package kr.co.won.service;

import kr.co.won.model.TodoEntity;
import kr.co.won.model.TodoRequest;
import kr.co.won.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoEntity add(TodoRequest request) {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setTitle(request.getTitle());
        todoEntity.setOrder(request.getOrder());
        todoEntity.setCompleted(request.getCompleted());
        return todoRepository.save(todoEntity);
    }

    public TodoEntity searchById(Long id) {
        TodoEntity findTodoEntity = todoRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        return findTodoEntity;
    }

    public List<TodoEntity> searchAll() {
        return todoRepository.findAll();
    }

    public TodoEntity updateById(Long id, TodoRequest request) {
        TodoEntity findTodoEntity = searchById(id);
        if (request.getTitle() != null) {
            findTodoEntity.setTitle(request.getTitle());
        }
        if (request.getOrder() != null) {
            findTodoEntity.setOrder(request.getOrder());
        }
        if (request.getCompleted() != null) {
            findTodoEntity.setCompleted(request.getCompleted());
        }
        return todoRepository.save(findTodoEntity);
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    public void deleteAll() {
        todoRepository.deleteAll();
    }
}
