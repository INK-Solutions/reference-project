package tech.seedz.template.service.exception;

public class EntityNotFoundException extends GenericAppException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    @Override
    public String errorCode() {
        return "entity-not-found-exception";
    }
}
