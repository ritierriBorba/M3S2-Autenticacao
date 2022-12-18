package br.com.futurodev.elaboracaosecurity.controller;

import br.com.futurodev.elaboracaosecurity.security.TokenService;
import br.com.futurodev.elaboracaosecurity.security.dto.LoginDto;
import br.com.futurodev.elaboracaosecurity.security.dto.TokenDto;
import br.com.futurodev.elaboracaosecurity.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Step 16 - Criar endpoint para realizar login
 */
@RestController
@RequestMapping(value = "login")
public class LoginController {

    @Autowired private LoginService loginService;

    @PostMapping
    public TokenDto post(@RequestBody LoginDto login) {
        return loginService.gerarToken(login);
    }

}
