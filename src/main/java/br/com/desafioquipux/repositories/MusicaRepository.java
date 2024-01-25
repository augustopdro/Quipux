package br.com.desafioquipux.repositories;

import br.com.desafioquipux.models.Lista;
import br.com.desafioquipux.models.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, Long> {
    @Query("SELECT m FROM Musica m WHERE m.titulo = ?1")
    Optional<Musica> findByName(String titulo);
}
