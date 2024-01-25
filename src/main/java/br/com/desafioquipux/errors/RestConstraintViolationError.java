package br.com.desafioquipux.errors;

public record RestConstraintViolationError(
        int code,
        Object field,
        String message
) {}
