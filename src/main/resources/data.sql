insert into user (id, name, email, pass_word, access_level)
    values (1, 'Roger', 'professor@email', '$2a$12$8wVvIVdWUgdIUsVKcMWhI.I5qN7JGT825U8Mqjfxf5viu5QLPy65u', 'PROFESSOR');
insert into user (id, name, email, pass_word, access_level)
    values (2, 'Pedro', 'aluno@email', '$2a$12$8wVvIVdWUgdIUsVKcMWhI.I5qN7JGT825U8Mqjfxf5viu5QLPy65u', 'ALUNO');

insert into task(id, description, status, user_id)
    values (1, 'Lista de tarefas', 'STARTED', 1);
insert into task(id, description, status, user_id)
    values (2, 'Lista de tarefas extras', 'FINISHED', 1);

insert into sub_task(id, description, status, task_id)
    values (1, 'tarefa 1', 'STARTED', 1);
insert into sub_task(id, description, status, task_id)
    values (2, 'tarefa 2', 'FINISHED', 1);
insert into sub_task(id, description, status, task_id)
    values (5, 'tarefa 3', 'FINISHED', 1);
insert into sub_task(id, description, status, task_id)
    values (3, 'tarefa 1', 'STARTED', 2);
insert into sub_task(id, description, status, task_id)
    values (4, 'tarefa 2', 'FINISHED', 2);