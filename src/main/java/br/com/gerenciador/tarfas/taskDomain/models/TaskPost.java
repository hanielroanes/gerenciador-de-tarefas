package br.com.gerenciador.tarfas.taskDomain.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class TaskPost {

    @NotBlank
    private String description;

}
