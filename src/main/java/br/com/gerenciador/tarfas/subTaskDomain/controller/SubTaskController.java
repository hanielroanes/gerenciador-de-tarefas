package br.com.gerenciador.tarfas.subTaskDomain.controller;

import br.com.gerenciador.tarfas.subTaskDomain.models.SubTaskDto;
import br.com.gerenciador.tarfas.subTaskDomain.models.SubTaskPost;
import br.com.gerenciador.tarfas.subTaskDomain.models.SubTaskPut;
import br.com.gerenciador.tarfas.subTaskDomain.services.SubTaskService;
import br.com.gerenciador.tarfas.taskDomain.models.TaskPost;
import br.com.gerenciador.tarfas.taskDomain.models.TaskPut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.lang.Long;

@Controller
@RequestMapping("/tasks/{taskId}/sub-tasks")
public class SubTaskController {

    @Autowired
    private SubTaskService subTaskService;

    @GetMapping
    public String getAll(
            Model model,
            @RequestParam(name = "limit", required = false) Integer limit,
            @RequestParam(name = "offset", required = false) Integer offset,
            @PathVariable("taskId") Long taskId){
        Pageable pageable = PageRequest.of(offset != null ? offset : 0
                , limit != null ? limit : 50);

        model.addAttribute("subTasks", subTaskService.getAll(taskId, pageable));
        model.addAttribute("taskId", taskId);
        return "sub-task/sub-task-menu";
    }

    @GetMapping("/post")
    public String create(Model model, @PathVariable("taskId") Long taskId,
                         @ModelAttribute("subTask") SubTaskPost subTaskPost){
        model.addAttribute("taskId", taskId);
        return "sub-task/sub-task-form";
    }

    @PostMapping
    public String post(@PathVariable("taskId") Long taskId,
            @Valid @ModelAttribute("subTask") SubTaskPost subTaskPost,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "sub-task/sub-task-form";
        }
        subTaskService.create(taskId, subTaskPost);
        return "redirect:/tasks/" + taskId + "/sub-tasks";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("taskId") Long taskId,
            @PathVariable("id") Long id){
        this.subTaskService.delete(taskId, id);
        return "redirect:/tasks/" + taskId + "/sub-tasks";
    }

    @GetMapping("/{id}/put")
    public String update(Model model, @PathVariable("id") Long id,
                         @PathVariable("taskId") Long taskId){
        model.addAttribute("subTask", subTaskService.getById(taskId, id));
        model.addAttribute("taskId", taskId);
        return "sub-task/sub-task-put-form";
    }

    @GetMapping("/{id}/alter-status")
    public String alterStatus(Model model, @PathVariable("id") Long id,
                         @PathVariable("taskId") Long taskId){
        subTaskService.alterStatus(taskId, id);
        model.addAttribute("taskId", taskId);
        return "redirect:/tasks/" + taskId + "/sub-tasks";
    }

    @PostMapping("/{id}")
    public String put(@PathVariable("id") Long id,
                      @PathVariable("taskId") Long taskId,
                      @Valid @ModelAttribute("subTask") SubTaskPut subTaskPut,
                      BindingResult result){
        if (result.hasErrors()){
            return "sub-task/sub-task-put-form";
        }
        this.subTaskService.update(taskId, id, subTaskPut);
        return "redirect:/tasks/" + taskId + "/sub-tasks";
    }

}
