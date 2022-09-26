package tech.seedz.template.service.exception;

public abstract class GenericAppException extends RuntimeException {
    public GenericAppException(String message) {
        super(message);
    }

    public abstract String errorCode();
}
