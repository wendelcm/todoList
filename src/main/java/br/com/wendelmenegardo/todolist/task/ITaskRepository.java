package br.com.wendelmenegardo.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByIdUser(UUID idUser);

    // TaskModel findByIdAndByIdUser(UUID idTask, UUID idUser);
    // é um outro modo de validar se o usuário que está tentando alterar o registro
    // é o usuário do id da task
}
