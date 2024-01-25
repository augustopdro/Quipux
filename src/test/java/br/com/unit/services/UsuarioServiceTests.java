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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTests {
    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @Test
    public void deveCadastrarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@email.com");
        usuario.setSenha("123456");

        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setId(1L);
        usuarioSalvo.setEmail(usuario.getEmail());
        usuarioSalvo.setSenha(passwordEncoder.encode(usuario.getSenha()));

        when(usuarioRepository.save(usuario)).thenReturn(usuarioSalvo);

        Usuario usuarioCadastrado = usuarioService.cadastrar(usuario);

        assertEquals(Optional.of(1L), usuarioCadastrado.getId());
        assertNotEquals("123456", usuarioCadastrado.getSenha()); // Senha foi encriptada
        verify(usuarioRepository).save(usuario);
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
    @DisplayName("Teste de login")
    public void deveLogarUsuarioComCredenciaisValidas() {
        LoginDTO credenciais = new LoginDTO("teste@email.com", "123456");
        Authentication authentication = credenciais.toAuthentication();
        TokenDTO tokenDTO = new TokenDTO("token", "Bearer", "PREFIX_");

        when(authenticationManager.authenticate(authentication)).thenReturn(authentication);
        when(tokenService.generateToken(credenciais)).thenReturn(tokenDTO);

        TokenDTO tokenGerado = usuarioService.logar(credenciais);

        assertEquals(tokenGerado, tokenDTO);
        verify(authenticationManager).authenticate(authentication);
        verify(tokenService).generateToken(credenciais);
    }
}
