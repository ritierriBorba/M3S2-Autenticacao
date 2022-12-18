package br.com.futurodev.elaboracaosecurity.security.dto;

import lombok.Builder;
import lombok.Data;

/*
 * Step 2 - Cria DTO Token
 */
@Data
@Builder
public class TokenDto {

    private String token;
    private String tipo; // Bearer

}
