package br.com.gerenciador.tarfas.subTaskDomain.models;

import br.com.gerenciador.tarfas.taskDomain.enums.Status;
import lombok.Data;

@Data
public class SubTaskDto {

    private Long id;

    private String description;

    private Status status;
}
