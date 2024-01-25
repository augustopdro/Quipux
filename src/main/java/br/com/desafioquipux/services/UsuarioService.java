package br.com.desafioquipux.services;

import br.com.desafioquipux.dtos.LoginDTO;
import br.com.desafioquipux.dtos.TokenDTO;
import br.com.desafioquipux.exceptions.RestNotFoundException;
import br.com.desafioquipux.models.Usuario;
import br.com.desafioquipux.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private UsuarioRepository repository;
    private AuthenticationManager manager;
    private PasswordEncoder encoder;
    private TokenService tokenService;

    @Autowired
    public UsuarioService(UsuarioRepository repository, AuthenticationManager manager, PasswordEncoder encoder, TokenService tokenService) {
        this.repository = repository;
        this.manager = manager;
        this.encoder = encoder;
        this.tokenService = tokenService;
    }

    public Usuario cadastrar(Usuario usuario)
    {
        log.info("Cadastrando usuario.");
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        repository.save(usuario);

        return repository.save(usuario);
    }


    public Usuario recuperar(long id) {
        log.info("Recuperando cadastro de usuario pelo id: " + id);

        Usuario usuario = repository
                .findById(id)
                .orElseThrow(() -> new RestNotFoundException("Usuario não encontrado"));

        return usuario;
    }

    public TokenDTO logar(LoginDTO credenciais) {
        manager.authenticate(credenciais.toAuthentication());

        return tokenService.generateToken(credenciais);
    }
}
