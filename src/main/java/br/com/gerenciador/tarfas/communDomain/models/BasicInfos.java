package br.com.gerenciador.tarfas.communDomain.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@Setter
public class BasicInfos {

    protected LocalDateTime createdDate = LocalDateTime.now();
    protected LocalDateTime updatedDate;

    @PreUpdate
    private void preUpdate(){
        updatedDate = LocalDateTime.now();
    }

}
