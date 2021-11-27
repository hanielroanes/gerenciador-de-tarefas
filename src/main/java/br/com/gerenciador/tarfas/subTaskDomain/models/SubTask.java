package br.com.gerenciador.tarfas.subTaskDomain.models;

import br.com.gerenciador.tarfas.communDomain.models.BasicInfos;
import br.com.gerenciador.tarfas.taskDomain.enums.Status;
import br.com.gerenciador.tarfas.taskDomain.models.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubTask extends BasicInfos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JoinColumn(name = "task_id",nullable = false, updatable = false)
    @ManyToOne
    private Task task;

    @PrePersist
    private void prePersist(){
        this.setStatus(Status.STARTED);
    }
}
