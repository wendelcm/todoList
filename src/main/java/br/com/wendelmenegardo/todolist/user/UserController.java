package br.com.wendelmenegardo.todolist.user;

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
    @PostMapping("/")
    public void create(@RequestBody UserModel userModel) {
        System.out.println(userModel.getUsername());
    }
}
