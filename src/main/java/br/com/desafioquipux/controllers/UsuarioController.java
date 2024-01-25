package br.com.desafioquipux.controllers;

import br.com.desafioquipux.dtos.LoginDTO;
import br.com.desafioquipux.dtos.TokenDTO;
import br.com.desafioquipux.dtos.UsuarioResponseDTO;
import br.com.desafioquipux.models.Usuario;
import br.com.desafioquipux.services.UsuarioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;


    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @PostMapping("cadastrar")
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> cadastrar(@Valid @RequestBody Usuario usuario)
    {
        var usuarioCadastrado = usuarioService.cadastrar(usuario);

        var responseDTO = new UsuarioResponseDTO(usuarioCadastrado.getId(), usuarioCadastrado.getEmail(), usuarioCadastrado.getSenha());

        var entityModel = EntityModel.of(
                responseDTO,
                linkTo(methodOn(UsuarioController.class).cadastrar(usuario)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).logar(new LoginDTO(usuarioCadastrado.getEmail(), usuarioCadastrado.getSenha()))).withRel("logar")
        );

        return ResponseEntity.created(linkTo(methodOn(UsuarioController.class).cadastrar(usuario)).toUri())
                .body(entityModel);
    }


    @PostMapping("login")
    public EntityModel<TokenDTO> logar(@Valid @RequestBody LoginDTO credenciais)
    {
        log.info("solicitando validação das credenciais informadas");

        TokenDTO responseService = usuarioService.logar(credenciais);

        return EntityModel.of(
                responseService,
                linkTo(methodOn(UsuarioController.class).logar(credenciais)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).cadastrar(new Usuario())).withRel("cadastrar")
        );
    }
}
