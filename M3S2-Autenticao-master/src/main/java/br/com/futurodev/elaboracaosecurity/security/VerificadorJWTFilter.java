package br.com.futurodev.elaboracaosecurity.security;

import br.com.futurodev.elaboracaosecurity.model.Usuario;
import br.com.futurodev.elaboracaosecurity.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * Step 19 - Filtro de requisições
 * Verificador autenticação de todas as requisições
 */
public class VerificadorJWTFilter extends OncePerRequestFilter {

    // Dependências
    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;

    // Construtor
    public VerificadorJWTFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request); // Extrai o token do cabeçalho
        if (tokenService.isTokenValido(token)) { // Verifica se token é válido
            autenticarCliente(token);
        }
        // Segue para o próximo filtro
        filterChain.doFilter(request, response);
    }

    // Método indica ao spring security qual usuário logado via JWT
    private void autenticarCliente(String token) {
        Integer usuarioId = tokenService.getUsuarioId(token);
        Usuario usuario = usuarioRepository.buscarPorId(usuarioId);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    // Extrai o token do cabeçalho
    private String recuperarToken(HttpServletRequest request) {
        String tipo = "Bearer";
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith(tipo)) {
            return null;
        }
        //Extrai o token
        return token.replaceFirst(tipo, "").trim();
    }

}
