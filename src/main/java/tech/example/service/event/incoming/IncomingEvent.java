package tech.example.service.event.incoming;

import lombok.Getter;

@Getter
public abstract class IncomingEvent {
    private final String id;

    protected IncomingEvent(String id) {
        this.id = id;
    }
}
