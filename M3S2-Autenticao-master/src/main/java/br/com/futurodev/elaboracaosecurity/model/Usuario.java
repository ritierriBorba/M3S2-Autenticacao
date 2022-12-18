package br.com.futurodev.elaboracaosecurity.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * Step 5 - Model Usuário
 * ... implements UserDetails ...
 */
@Data
@Builder
public class Usuario implements UserDetails {

    private Integer id;
    private String nome;
    private String login;
    private String senha;
    private List<String> permissoes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> perfil = new ArrayList<>();
        try {
            for (String permissao : permissoes) {
                GrantedAuthority grantedAuthority = () -> permissao;
                perfil.add(grantedAuthority);
            }
        } catch (Exception e) {
        }
        return perfil;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
