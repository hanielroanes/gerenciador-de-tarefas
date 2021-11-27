package br.com.gerenciador.tarfas.taskDomain.models;

import br.com.gerenciador.tarfas.communDomain.models.BasicInfos;
import br.com.gerenciador.tarfas.taskDomain.enums.Status;
import br.com.gerenciador.tarfas.subTaskDomain.models.SubTask;
import br.com.gerenciador.tarfas.userDomain.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task extends BasicInfos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
    private Set<SubTask> subTasks = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    private void prePersist(){
        this.setStatus(Status.STARTED);
    }

}
