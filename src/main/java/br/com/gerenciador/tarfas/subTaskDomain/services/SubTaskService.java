package br.com.gerenciador.tarfas.subTaskDomain.services;

import br.com.gerenciador.tarfas.communDomain.utils.MapperUtil;
import br.com.gerenciador.tarfas.taskDomain.enums.Status;
import br.com.gerenciador.tarfas.taskDomain.repository.TaskRepository;
import br.com.gerenciador.tarfas.subTaskDomain.models.SubTask;
import br.com.gerenciador.tarfas.subTaskDomain.models.SubTaskDto;
import br.com.gerenciador.tarfas.subTaskDomain.models.SubTaskPost;
import br.com.gerenciador.tarfas.subTaskDomain.models.SubTaskPut;
import br.com.gerenciador.tarfas.subTaskDomain.repository.SubTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.lang.Long;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class SubTaskService {

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MapperUtil mapperUtil;

    @Transactional(readOnly = true)
    public Page<SubTaskDto> getAll(Long taskId, Pageable pageable) {
        this.exisistTaskById(taskId);
        return mapperUtil.toPage
                (subTaskRepository.findByTaskId(taskId, pageable), SubTaskDto.class);
    }

    @Transactional(readOnly = true)
    public SubTaskDto getById(Long taskId, Long subTaskId){
        this.exisistTaskById(taskId);
        return mapperUtil.mapTo(this.getSubTaskDomain(subTaskId)
                , SubTaskDto.class);
    }

    @Transactional
    public SubTaskDto create(Long taskId, SubTaskPost subTaskPost){
        this.exisistTaskById(taskId);
        var subTask = mapperUtil.mapTo(subTaskPost, SubTask.class);
        subTask.setTask(taskRepository.getById(taskId));
        return mapperUtil.mapTo(subTaskRepository.save(subTask),
                SubTaskDto.class);
    }

    @Transactional
    public SubTaskDto update(Long taskId, Long subTaskId,
                             SubTaskPut subTaskPut){
        this.exisistTaskById(taskId);
        var subTaskDomain = getSubTaskDomain(subTaskId);
        var updateSubTask = mapperUtil.strictMapTo(subTaskPut, SubTask.class);
        var updatedSubTask = mapperUtil.updateTo(updateSubTask, subTaskDomain);

        return mapperUtil.mapTo(subTaskRepository.save(updatedSubTask),
                SubTaskDto.class);
    }

    @Transactional
    public void delete(Long taskId, Long subTaskId){
        this.exisistTaskById(taskId);
        var subTaskDomain = getSubTaskDomain(subTaskId);
        subTaskRepository.delete(subTaskDomain);
    }



    private SubTask getSubTaskDomain(Long subTaskId) {
        return subTaskRepository.findById(subTaskId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "sub_task_not_found"));
    }

    private void exisistTaskById(Long taskId){
        if (!taskRepository.existsById(taskId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "task_not_found");
    }

    public void alterStatus(Long taskId, Long id) {
        exisistTaskById(taskId);
        var subTask = getSubTaskDomain(id);

        if (subTask.getTask().getStatus().equals(Status.STARTED)){
            subTask.setStatus( subTask.getStatus().equals(Status.STARTED) ?
                    Status.FINISHED : Status.STARTED);

            subTaskRepository.save(subTask);
            this.verifySubTasksForTask(subTask);
        }

    }

    private void verifySubTasksForTask(SubTask subTask) {
        AtomicBoolean taskCompleted = new AtomicBoolean(true);
        var subTasks = subTaskRepository.findByTaskId(subTask.getTask().getId());

        subTasks.forEach(sub -> {
            if (sub.getStatus().equals(Status.STARTED))
                taskCompleted.set(false);
        });

        if (taskCompleted.get()){
            var task = subTask.getTask();
            task.setStatus(Status.FINISHED);
            taskRepository.save(task);
        }

    }
}
