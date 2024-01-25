package br.com.desafioquipux.services;

import br.com.desafioquipux.dtos.AdicionarMusicaDTO;
import br.com.desafioquipux.exceptions.RestNotFoundException;
import br.com.desafioquipux.models.Lista;
import br.com.desafioquipux.models.Musica;
import br.com.desafioquipux.models.Usuario;
import br.com.desafioquipux.repositories.ListaRepository;
import br.com.desafioquipux.repositories.MusicaRepository;
import br.com.desafioquipux.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListaService {

    Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private ListaRepository repository;
    private UsuarioRepository usuarioRepository;
    private MusicaRepository musicaRepository;

    @Autowired
    public ListaService(ListaRepository repository, UsuarioRepository usuarioRepository, MusicaRepository musicaRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.musicaRepository = musicaRepository;
    }

    public Lista criarLista(Lista lista, long userId){
        var usuario = recuperarUsuario(userId);
        lista.setUsuario(usuario);
        usuario.getListas().add(lista);
        return salvarLista(lista);
    }

    public Lista buscarLista(String nome){
        log.info("buscando lista com o nome: " + nome);

        Lista lista = repository
                .findByName(nome)
                .orElseThrow(() -> new RestNotFoundException("Lista não encontrada"));

        return lista;
    }

    public void deletarLista(String nome){
        var lista = buscarLista(nome);
        var usuario = lista.getUsuario();

        usuario.getListas().remove(lista);
        usuarioRepository.save(usuario);
        repository.delete(lista);
    }

    public Lista salvarMusicaNaLista(AdicionarMusicaDTO musicaDTO){
        var lista = buscarLista(musicaDTO.nomeDaLista());
        var musica = buscarMusica(musicaDTO.nomeDaMusica());

        if (lista == null || musica == null) {
            throw new IllegalArgumentException("Lista ou música não encontrada");
        }
        lista.getMusicas().add(musica);
        return salvarLista(lista);
    }

    private Usuario recuperarUsuario(long userId) {
        log.info("Recuperando usuario com id: " + userId);

        Usuario usuario = usuarioRepository
                .findById(userId)
                .orElseThrow(() -> new RestNotFoundException("Usuario não encontrado"));

        return usuario;
    }

    private Musica buscarMusica(String nome) {
        log.info("Recuperando musica com nome: " + nome);

        Musica musica = musicaRepository
                .findByName(nome)
                .orElseThrow(() -> new RestNotFoundException("Musica não encontrada"));

        return musica;
    }

    private Lista salvarLista(Lista lista){
        log.info("Registrando uma playlist: " + lista);
        return repository.save(lista);
    }
}
