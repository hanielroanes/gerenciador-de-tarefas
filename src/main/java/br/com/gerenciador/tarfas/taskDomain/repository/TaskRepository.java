package br.com.gerenciador.tarfas.taskDomain.repository;

import br.com.gerenciador.tarfas.taskDomain.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
