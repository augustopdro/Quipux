package br.com.desafioquipux.repositories;

import br.com.desafioquipux.models.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, Long> {
}
