package br.com.wendelmenegardo.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * modificador
 * public
 * private
 * protected
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    /**
     * String
     * Tnteger
     * Double
     * Float
     * Char
     * Date
     * void
     */

    /**
     * body
     */

    // acessar o h2-database depois de iniciar a aplicação: http://localhost:(porta
    // definida|Padrão:8080)/h2-console
    @PostMapping("/")
    public ResponseEntity create(@RequestBody UserModel userModel) {

        var user = this.userRepository.findByUsername(userModel.getUsername());

        if (user != null) {
            //Mensagem de erro
            //status code
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!");
        }

        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
