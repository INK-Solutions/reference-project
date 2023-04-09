package tech.example.service;

public abstract class GenericException extends RuntimeException {
    public GenericException(String message) {
        super(message);
    }

    public abstract String errorCode();
}
