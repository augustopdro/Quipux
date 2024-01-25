package br.com.desafioquipux.services;

import br.com.desafioquipux.exceptions.RestNotFoundException;
import br.com.desafioquipux.models.Lista;
import br.com.desafioquipux.models.Usuario;
import br.com.desafioquipux.repositories.ListaRepository;
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

    @Autowired
    public ListaService(ListaRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
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
                .orElseThrow(() -> new RestNotFoundException("Usuario não encontrado"));

        return lista;
    }

    public void deletarLista(String nome){
        var lista = buscarLista(nome);
        var usuario = lista.getUsuario();

        usuario.getListas().remove(lista);
        usuarioRepository.save(usuario);
        repository.delete(lista);
    }

    private Usuario recuperarUsuario(long userId) {
        log.info("Recuperando usuario com id: " + userId);

        Usuario usuario = usuarioRepository
                .findById(userId)
                .orElseThrow(() -> new RestNotFoundException("Usuario não encontrado"));

        return usuario;
    }

    private Lista salvarLista(Lista lista){
        log.info("Registrando uma playlist: " + lista);
        return repository.save(lista);
    }
}
