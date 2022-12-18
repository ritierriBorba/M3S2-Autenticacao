package br.com.futurodev.elaboracaosecurity.security.dto;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/*
 * Step 1 - Cria DTO Login
 */
@Data
public class LoginDto {

    private String login;
    private String senha;

    /*
     * Step 13 - Autenticação com Token
     * Converte no objeto para utilizar na autenticação e criação de JWT
     */
    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(this.login, this.senha);
    }

}
