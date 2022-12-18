package br.com.futurodev.elaboracaosecurity.security;

import br.com.futurodev.elaboracaosecurity.security.dto.LoginDto;
import br.com.futurodev.elaboracaosecurity.security.dto.TokenDto;
import br.com.futurodev.elaboracaosecurity.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

/*
 * Classe responsável pelo JWT
 */
@Service
public class TokenService {

    /*
     * Step 10 - JWT Service
     * Classe responsável pelo JWT
     */
    @Value("${app.jwt.expiration}")
    private String jwtExpiration;

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    /*
     * Step 12 - Gerar JWT
     * Método responsável por Criar JWT
     */
    public TokenDto gerarToken(Authentication auth) {

        // Pega o usuário atualmente logado
        Usuario usuarioLogado = (Usuario) auth.getPrincipal();

        // Utiliza as datas para indicar data de criação e vencimento do JWT
        Date inicioToken = new Date();
        Date terminoToken = new Date(inicioToken.getTime() + Long.parseLong(jwtExpiration));

        String jwt = Jwts.builder()
                .setIssuer("FuturoDev-Security") // Aplicação que criou token
                .setSubject(usuarioLogado.getId().toString()) // Identificador do usuário logado
                .setIssuedAt(inicioToken)
                .setExpiration(terminoToken)
                .signWith(SignatureAlgorithm.HS256, jwtSecret) // Assina o JWT com a Secret do passo 9
                .compact();

        return TokenDto.builder()
                .token(jwt)
                .tipo("Bearer")
                .build();
    }

    /*
     * Step 17 - Verificação de JWT válido
     * Verificador de JWT
     */
    public boolean isTokenValido(String token) {
        try {
            // Se a função "parseClaimsJws" jogar exceção então o token é inválido
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * Step 18 - Recupera id do usuário por token
     * Recupera id do usuário por token
     */
    public Integer getUsuarioId(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return Integer.parseInt(claims.getSubject());
    }
}
