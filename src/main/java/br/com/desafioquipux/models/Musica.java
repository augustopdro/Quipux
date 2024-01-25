package br.com.desafioquipux.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String titulo;

    @NotNull
    @Column(nullable = false)
    private String artista;

    @NotNull
    @Column(nullable = false)
    private String album;

    @NotNull
    @Column(nullable = false)
    private LocalDate ano;

    @NotNull
    @Column(nullable = false)
    private String genero;

    @ManyToMany
    @JoinTable(
            name = "musica_lista",
            joinColumns = @JoinColumn(name = "musica_id"),
            inverseJoinColumns = @JoinColumn(name = "lista_id"))
    private List<Lista> listas;
}
