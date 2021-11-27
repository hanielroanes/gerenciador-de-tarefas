package br.com.gerenciador.tarfas.taskDomain.controller;

import br.com.gerenciador.tarfas.taskDomain.models.TaskDto;
import br.com.gerenciador.tarfas.taskDomain.models.TaskPost;
import br.com.gerenciador.tarfas.taskDomain.models.TaskPut;
import br.com.gerenciador.tarfas.taskDomain.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String getAll(
            Model model,
            @RequestParam(name = "limit", required = false) Integer limit,
            @RequestParam(name = "offset", required = false) Integer offset){
        Pageable pageable = PageRequest.of(offset != null ? offset : 0
                , limit != null ? limit : 50);

        model.addAttribute("tasks", taskService.getAll(pageable));
        return "task/task-menu";
    }

    @GetMapping("/post")
    public String create(@ModelAttribute("task") TaskPost taskPost){
        return "task/task-form";
    }

    @PostMapping
    public String post(
            @Valid @ModelAttribute("task") TaskPost taskPost,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "task/task-form";
        }
        taskService.create(taskPost);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        this.taskService.delete(id);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}/put")
    public String update(Model model, @PathVariable Long id){
        model.addAttribute("task", taskService.getById(id));
        return "task/task-put-form";
    }

    @GetMapping("/{id}/alter-status")
    public String alterStatus(Model model, @PathVariable Long id){
        taskService.alterStatus(id);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}")
    public String put(@PathVariable Long id,  @Valid @ModelAttribute("task") TaskPut taskPut,
                      BindingResult result){
        if (result.hasErrors()){
            return "task/task-put-form";
        }
        this.taskService.update(id, taskPut);
        return "redirect:/tasks";
    }



}
