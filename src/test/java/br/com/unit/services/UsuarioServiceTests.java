package br.com.unit.services;
import br.com.desafioquipux.dtos.LoginDTO;
import br.com.desafioquipux.dtos.TokenDTO;
import br.com.desafioquipux.models.Usuario;
import br.com.desafioquipux.repositories.UsuarioRepository;
import br.com.desafioquipux.services.TokenService;
import br.com.desafioquipux.services.UsuarioService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTests {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;
    private TokenService tokenService;

    @Test
    public void cadastrar_Deve_RetornarUsuarioCadastrado() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("fulano@example.com");
        usuario.setSenha("senha123");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario resultado = usuarioService.cadastrar(usuario);

        assertNotNull(resultado);
        assertEquals(usuario.getId(), resultado.getId());
        assertEquals(usuario.getEmail(), resultado.getEmail());
        assertEquals(usuario.getSenha(), resultado.getSenha());
    }

    @Test
    public void testRecuperar() {
        long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail("fulano@example.com");
        usuario.setSenha("senha123");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.recuperar(id);

        verify(usuarioRepository, times(1)).findById(id);
        assertEquals(usuario, resultado);
    }

    @Test
    @DisplayName("Testa login de usuário")
    public void testLogar() {
        // Preparação
        String email = "fulano@exemplo.com";
        String senha = "senhasecreta123";
        String token = "token_gerado";

        LoginDTO credenciais = new LoginDTO(email, senha);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        when(usuarioRepository.buscarCredenciais(email, senha)).thenReturn(Optional.of(usuario));
        when(tokenService.generateToken(credenciais)).thenReturn(new TokenDTO(token, "Bearer", "Bearer "));

        TokenDTO resultado = usuarioService.logar(credenciais);

        verify(usuarioRepository, times(1)).buscarCredenciais(email, senha);
        assertNotNull(resultado);
        assertEquals(token, resultado.token());
    }
}
