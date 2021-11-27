package br.com.gerenciador.tarfas.taskDomain.models;

import br.com.gerenciador.tarfas.taskDomain.enums.Status;
import br.com.gerenciador.tarfas.subTaskDomain.models.SubTaskDto;
import br.com.gerenciador.tarfas.userDomain.models.UserDto;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class TaskDto {

    private Long id;

    private String description;

    private Status status;

    private Set<SubTaskDto> scheduligs = new HashSet<>();

    private UserDto user;
}
