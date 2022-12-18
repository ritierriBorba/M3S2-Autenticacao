package br.com.futurodev.elaboracaosecurity.repository;

import br.com.futurodev.elaboracaosecurity.model.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {

    private static int indice = 1;
    private static List<Usuario> usuarios = new ArrayList<>();

    private int gerarId() {
        return indice++;
    }
    private List<Usuario> getUsuarios() {
        // Sempre cria um usuário caso não exista nenhum
        if (usuarios.size() == 0) {
            List<String> permissoes = new ArrayList<>();
            permissoes.add("ALUNOS");
            permissoes.add("PROFESSORES");
            usuarios.add(Usuario.builder()
                    .id(gerarId())
                    .nome("admin")
                    .login("admin")
                    .senha(new BCryptPasswordEncoder().encode("123"))
                    .permissoes(permissoes)
                    .build());
        }
        return usuarios;
    }

    public Usuario buscarPorId(Integer id) {
        if (id == null)
            return null;
        for (Usuario usuario : getUsuarios()) {
            if (id.equals(usuario.getId())) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario buscarPorLogin(String login) {
        if (login == null || login.isEmpty())
            return null;
        for (Usuario usuario : getUsuarios()) {
            if (login.equals(usuario.getLogin())) {
                return usuario;
            }
        }
        return null;
    }

}
