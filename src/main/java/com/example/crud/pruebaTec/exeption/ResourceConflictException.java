package com.example.crud.pruebaTec.exeption;

public class ResourceConflictException extends RuntimeException {
    public ResourceConflictException(String message) {
        super(message);
    }
}
