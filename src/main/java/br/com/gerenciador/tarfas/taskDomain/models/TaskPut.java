package br.com.gerenciador.tarfas.taskDomain.models;

import br.com.gerenciador.tarfas.taskDomain.enums.Status;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TaskPut {

    @NotBlank
    private String description;

}
