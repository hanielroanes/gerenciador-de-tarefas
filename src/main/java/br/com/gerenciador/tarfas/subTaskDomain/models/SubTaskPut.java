package br.com.gerenciador.tarfas.subTaskDomain.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SubTaskPut {

    @NotBlank
    private String description;

}
