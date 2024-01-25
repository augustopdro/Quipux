package br.com.desafioquipux.services;


import br.com.desafioquipux.models.Musica;
import br.com.desafioquipux.repositories.MusicaRepository;
import br.com.desafioquipux.repositories.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MusicaService {
    Logger log = LoggerFactory.getLogger(MusicaService.class);

    private MusicaRepository repository;
    private UsuarioRepository usuarioRepository;

    @Autowired
    public MusicaService(MusicaRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    public Musica criarMusica(Musica musica){
        return salvarMusica(musica);
    }


    private Musica salvarMusica(Musica musica){
        log.info("Registrando uma musica: " + musica);
        return repository.save(musica);
    }
}
