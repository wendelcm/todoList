package br.com.wendelmenegardo.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import br.com.wendelmenegardo.todolist.utils.utils;

@RestController
@RequestMapping("tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        System.out.println("Chegou no controller: " + idUser);
        taskModel.setIdUser((UUID) idUser); // passa o id do usuário para o user model || (UUID) -> faz a conversão do
                                            // idUser para um UUID pos no model foi defindo que idUser era um UUID

        var currentDate = LocalDateTime.now();

        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data(inicio / término) deve ser maior que a data atual!");
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de inicio deve ser menor que a data de término!");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }

    @PutMapping("/{id}") // Vai verificar qual o idTask do metódo e atribuir a anotation.
    public TaskModel update(@RequestBody TaskModel taskModel, @PathVariable UUID idTask, HttpServletRequest request) {
        // metódo para fazer o put é atualizar uma tarefa.
        var task = this.taskRepository.findById(idTask).orElse(null);

        utils.copyNonNullProperties(taskModel, task);

        return this.taskRepository.save(task); // Como não tem update no taskRepository a medida é utilizar o save.
    }
}
