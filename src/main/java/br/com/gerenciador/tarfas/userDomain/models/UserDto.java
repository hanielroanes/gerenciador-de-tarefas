package br.com.gerenciador.tarfas.userDomain.models;

import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;
}
