package br.com.gerenciador.tarfas.taskDomain.services;

import br.com.gerenciador.tarfas.communDomain.utils.MapperUtil;
import br.com.gerenciador.tarfas.taskDomain.enums.Status;
import br.com.gerenciador.tarfas.taskDomain.models.Task;
import br.com.gerenciador.tarfas.taskDomain.models.TaskDto;
import br.com.gerenciador.tarfas.taskDomain.models.TaskPost;
import br.com.gerenciador.tarfas.taskDomain.models.TaskPut;
import br.com.gerenciador.tarfas.taskDomain.repository.TaskRepository;
import br.com.gerenciador.tarfas.userDomain.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MapperUtil mapperUtil;

    @Transactional(readOnly = true)
    public Page<TaskDto> getAll(Pageable pageable) {
        Page<TaskDto> scheduleDtos = mapperUtil.toPage
                (taskRepository.findAll(pageable), TaskDto.class);

        return scheduleDtos;
    }

    @Transactional(readOnly = true)
    public TaskDto getById(Long scheduleId){
        return mapperUtil.mapTo(this.getDomain(scheduleId)
                , TaskDto.class);
    }

    @Transactional
    public TaskDto create(TaskPost taskPost){
        var task = mapperUtil.mapTo(taskPost, Task.class);
        User user = new User();
        user.setId(1L);
        task.setUser(user);
        return mapperUtil.mapTo(taskRepository.save(task),
                TaskDto.class);
    }

    @Transactional
    public TaskDto update(Long taskId, TaskPut taskPut){
        var taskDomain = getDomain(taskId);
        var updateTask = mapperUtil.mapTo(taskPut, TaskDto.class);
        var updatedTask = mapperUtil.updateTo(updateTask, taskDomain);

        return mapperUtil.mapTo(taskRepository.save(updatedTask),
                TaskDto.class);
    }

    @Transactional
    public void delete(Long taskId){
        var taskDomain = getDomain(taskId);
        taskRepository.delete(taskDomain);
    }

    private Task getDomain(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "task_not_found"));
    }

    public void alterStatus(Long id) {
        var task = getDomain(id);

        if (task.getStatus().equals(Status.STARTED)){
            task.setStatus(task.getSubTasks().isEmpty() ?
                    Status.FINISHED : Status.STARTED);
        }else{

            if (task.getSubTasks().isEmpty()){
                task.setStatus(Status.STARTED);
            }else{
                AtomicBoolean subTasksIsFinished = new AtomicBoolean(true);
                task.getSubTasks().forEach( subTask -> {
                    if (subTask.getStatus().equals(Status.STARTED))
                        subTasksIsFinished.set(false);
                });

                task.setStatus(subTasksIsFinished.get() ? Status.FINISHED : Status.STARTED);
            }

        }

        taskRepository.save(task);

    }
}
