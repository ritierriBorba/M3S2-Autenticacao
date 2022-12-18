package br.com.futurodev.elaboracaosecurity.security;

import br.com.futurodev.elaboracaosecurity.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
 * Step 3 - Configuração
 * @Configuration
 * @EnableWebSecurity
 * ... extends WebSecurityConfigurerAdapter
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*
     * Step 7 - Configurar autenticação
     * Injeta a dependência da classe com a lógica de busca de usuário
     */
    @Autowired private AutenticacaoService autenticacaoService;

    /*
     * Step 20 - Dependências do filtro
     * Injeta as dependências para uso no filtro de requisições
     */
    @Autowired private TokenService tokenService;
    @Autowired private UsuarioRepository usuarioRepository;

    /*
     * Step 4 - Configurar autorizações
     * Sobrecrever o método configure
     * ... configure(HttpSecurity http) ...
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll() // endpoint "login" liberado à todos
                .antMatchers("/alunos").hasAnyAuthority("ALUNOS") // Liberado somente para usuários com a devida autorização
                .antMatchers("/professores").hasAnyAuthority("PROFESSORES") // Liberado somente para usuários com a devida autorização
                .anyRequest().authenticated() // Todos os demais endpoints precisam de autentição
                .and().csrf().disable() // Desabilita verificação CSRF (Cross-Site Request Forgery) do token
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Indica que a autenticação será Stateless (sem sessão)

                /*
                 * Step 21 - Filtro de requisições
                 * Indica a ordem de execução do filtro
                 */
                .and().addFilterBefore(new VerificadorJWTFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
    }

    /*
     * Step 8 - Configurar autenticação
     * ... configure(AuthenticationManagerBuilder auth) ...
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Indica onde buscar o usuário e a criptografia da senha utilizada
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /*
     * Step 14 - Gerenciador de autenticação
     * Método que cria um authenticationManager() para utilizarmos como Bean
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
