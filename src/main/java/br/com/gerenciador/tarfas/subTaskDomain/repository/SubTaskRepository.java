package br.com.gerenciador.tarfas.subTaskDomain.repository;

import br.com.gerenciador.tarfas.subTaskDomain.models.SubTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, Long> {
    Page<SubTask> findByTaskId(Long taskId, Pageable pageable);
    List<SubTask> findByTaskId(Long taskId);
}
