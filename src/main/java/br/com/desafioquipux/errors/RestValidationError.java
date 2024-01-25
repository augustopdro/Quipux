package br.com.desafioquipux.errors;

public record RestValidationError(
        Integer code,
        String field,
        String message
) {}
