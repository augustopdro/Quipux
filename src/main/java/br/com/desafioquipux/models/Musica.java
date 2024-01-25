package br.com.desafioquipux.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String ano;

    @NotNull
    @Column(nullable = false)
    private String genero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musica_id")
    @JsonIgnore
    private Lista lista;

    @Override
    public String toString() {
        return "Musica{" +
                "titulo='" + titulo + '\'' +
                ", artista='" + artista + '\'' +
                ", album='" + album + '\'' +
                ", ano='" + ano + '\'' +
                ", genero='" + genero + '\'' +
                '}';
    }
}
