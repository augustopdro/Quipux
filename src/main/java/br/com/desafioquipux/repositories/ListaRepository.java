package br.com.desafioquipux.repositories;

import br.com.desafioquipux.models.Lista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListaRepository extends JpaRepository<Lista, Long> {
    @Query("SELECT l FROM Lista l WHERE l.nome = ?1")
    Optional<Lista> findByName(String nome);

    @Query("SELECT l FROM Lista l WHERE l.usuario.id = :usuarioId")
    Page<Lista> getAllRegisters(long usuarioId , Pageable pageable);
}
