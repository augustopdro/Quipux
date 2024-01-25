package br.com.desafioquipux.repositories;

import br.com.desafioquipux.models.Lista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListaRepository extends JpaRepository<Lista, Long> {
    @Query("SELECT l FROM Lista l WHERE l.nome = ?1")
    Optional<Lista> buscarLista(String nome);
}
