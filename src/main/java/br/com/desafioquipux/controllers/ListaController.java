package br.com.desafioquipux.controllers;

import br.com.desafioquipux.dtos.AdicionarMusicaDTO;
import br.com.desafioquipux.dtos.PaginationResponseDTO;
import br.com.desafioquipux.exceptions.RestBadRequestException;
import br.com.desafioquipux.models.Lista;
import br.com.desafioquipux.models.Musica;
import br.com.desafioquipux.services.HistoricoService;
import br.com.desafioquipux.services.ListaService;
import br.com.desafioquipux.services.MusicaService;
import br.com.desafioquipux.services.UsuarioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/lista")
public class ListaController {

    private ListaService listaService;
    private MusicaService musicaService;
    private HistoricoService historicoService;
    private UsuarioService usuarioService;

    private Logger log = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    public ListaController(ListaService listaService, MusicaService musicaService, HistoricoService historicoService, UsuarioService usuarioService) {
        this.listaService = listaService;
        this.musicaService = musicaService;
        this.historicoService = historicoService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("{userId}/lists")
    public ResponseEntity<EntityModel<Lista>> criarLista(@Valid @RequestBody Lista lista, @PathVariable long userId)
    {
        log.info("Criando lista");
        Lista responseService = listaService.criarLista(lista, userId);

        var entityModel = EntityModel.of(
                responseService,
                linkTo(methodOn(ListaController.class).criarLista(lista, userId)).withSelfRel(),
                linkTo(methodOn(ListaController.class).buscarLista(userId, lista.getNome())).withRel("recuperar")
        );

        return ResponseEntity.created(linkTo(methodOn(ListaController.class).criarLista(lista, userId)).toUri())
                .body(entityModel);
    }

    @PostMapping("{userId}/musica")
    public ResponseEntity<EntityModel<Musica>> criarMusica(@Valid @RequestBody Musica musica, @PathVariable long userId)
    {
        log.info("Criando musica");
        Musica responseService = musicaService.criarMusica(musica);

        var entityModel = EntityModel.of(
                responseService,
                linkTo(methodOn(ListaController.class).criarMusica(musica, userId)).withSelfRel()
        );

        return ResponseEntity.created(linkTo(methodOn(ListaController.class).criarMusica(musica, userId)).toUri())
                .body(entityModel);
    }

    @PutMapping("{userId}/AdicionarMusica")
    public EntityModel<Lista> adicionarMusica(@Valid @RequestBody AdicionarMusicaDTO musicaDTO, @PathVariable long userId)
    {
        log.info("Adicionando na playlist");
        Lista responseService = listaService.salvarMusicaNaLista(musicaDTO);

        if(responseService == null)
            throw new RestBadRequestException("Musica não inserida. Tente novamente com dados diferentes.");

        return EntityModel.of(
                responseService,
                linkTo(methodOn(ListaController.class).adicionarMusica(musicaDTO, userId)).withSelfRel()
        );

    }


    @GetMapping("{userId}/lists")
    public ResponseEntity<EntityModel<PaginationResponseDTO>> recuperarHistorico(@PathVariable long userId, @PageableDefault(size = 3) Pageable pageable) {
        log.info("Buscando historico");

        var historicoEncontrado = historicoService.recuperarHistorico(userId, pageable);

        var entityModel = EntityModel.of(
                historicoEncontrado,
                linkTo(methodOn(ListaController.class).recuperarHistorico(userId, pageable)).withSelfRel()
        );

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("{userId}/lists/{listName}")
    public EntityModel<Lista> buscarLista(@PathVariable long userId, @PathVariable String listName)
    {
        log.info("Buscando Lista");
        var lista = listaService.buscarLista(listName);

        return EntityModel.of(
                lista,
                linkTo(methodOn(ListaController.class).buscarLista(userId, listName)).withSelfRel(),
                linkTo(methodOn(ListaController.class).criarLista(lista, userId)).withRel("criar"),
                linkTo(methodOn(ListaController.class).deletarLista(userId, listName)).withRel("deletar")
        );
    }

    @DeleteMapping("{userId}/lists/{listName}")
    public ResponseEntity<Lista> deletarLista(@PathVariable long userId, @PathVariable String listName)
    {
        log.info("Deletando lista");

        listaService.deletarLista(listName);
        return ResponseEntity.noContent().build();
    }
}
