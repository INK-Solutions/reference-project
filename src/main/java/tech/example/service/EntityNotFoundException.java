package tech.example.service;

public class EntityNotFoundException extends GenericException{
    public EntityNotFoundException(String message) {
        super(message);
    }

    @Override
    public String errorCode() {
        return "entity-not-found";
    }
}
