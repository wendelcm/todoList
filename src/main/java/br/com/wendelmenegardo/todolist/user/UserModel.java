package br.com.wendelmenegardo.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

// @Getter - gera somente os Get || @Setter - Gera somente os Set
// gera os Getter e Setter
@Data
@Entity(name = "TB_USERS")
public class UserModel {
 
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    // @Colunm(name="nome_da_coluna") caso queira dar um nome diferente do que está
    // na definição abaixo para a coluna
    @Column(unique = true) //a coluna com a anotation não pode haver outro existe no DB
    private String username;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
