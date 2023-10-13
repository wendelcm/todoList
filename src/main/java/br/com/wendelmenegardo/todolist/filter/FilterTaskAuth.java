package br.com.wendelmenegardo.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.wendelmenegardo.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // captura a rota.
        var servletPath = request.getServletPath();
        // valida a rota
        if (servletPath.startsWith("/tasks/")) {

            // pegar a autenticação (user e pass)
            var authorization = request.getHeader("Authorization"); // captura todo o campo passado no atributo
                                                                    // Authorization no Header da request.
            var authEncoded = authorization.substring("Basic".length()).trim(); // valida o tamanho do Basic e remove
                                                                                // ele do
                                                                                // restante da string || trim() ->
                                                                                // remove os
                                                                                // espaços
            byte[] authDecode = Base64.getDecoder().decode(authEncoded); // decodifica o token e atribui a um array de
                                                                         // bytes
            var authString = new String(authDecode); // Transforma o token que foi passado para bytes em uma string e
                                                     // retorna o usuário e senha!
            String[] credentials = authString.split(":"); // percorre a lista de credenciais e localiza o separador
            String username = credentials[0]; // retorna o usuário para uma variavel do tipo string passando a posição
                                              // dele
                                              // no array
            String password = credentials[1]; // retorna a senha para uma variavel do tipo string passando a posição
                                              // dele no
                                              // array

            System.out.println("Username:" + username + " || Password:" + password);

            // validar usuário
            var user = this.userRepository.findByUsername(username); // valida a existencia do usuário da requisição que
                                                                     // está salvo na variavel username.
            if (user == null) {
                response.sendError(401); // caso não exista retorna um erro no console que o usuário não tem autorização
            } else {
                // validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    // segue viagem
                    request.setAttribute("idUser", user.getId()); // Seta o id do usuário no request.
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }

            }

            filterChain.doFilter(request, response);

        } else {
            filterChain.doFilter(request, response);
        } 

    }
}