package kr.co.won.repository;

import kr.co.won.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
}
