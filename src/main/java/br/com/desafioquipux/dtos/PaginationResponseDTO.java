package br.com.desafioquipux.dtos;

import br.com.desafioquipux.models.Lista;

import java.util.List;

public record PaginationResponseDTO(
        List<Lista> content,
        int number,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {}
