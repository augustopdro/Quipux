package br.com.desafioquipux.services;

import br.com.desafioquipux.dtos.PaginationResponseDTO;
import br.com.desafioquipux.repositories.ListaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HistoricoService {

    Logger log = LoggerFactory.getLogger(HistoricoService.class);
    private ListaRepository listaRepository;

    @Autowired
    public HistoricoService(ListaRepository listaRepository) {
        this.listaRepository = listaRepository;
    }

    public PaginationResponseDTO recuperarHistorico(long userId, Pageable pageable) {
        log.info("Buscando historico de listas do usuário: " + userId);

        var listas = listaRepository.getAllRegisters(userId, pageable);

        return new PaginationResponseDTO(
                listas.getContent(),
                listas.getNumber(),
                listas.getTotalElements(),
                listas.getTotalPages(),
                listas.isFirst(),
                listas.isLast()
        );
    }
}
