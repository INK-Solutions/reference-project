package tech.example.service.event.outgoing;

import java.util.Date;
import java.util.UUID;

public abstract class OutgoingEvent {
    private final String id = UUID.randomUUID().toString();
    private final Date creationTimestamp = new Date();

    public String getId() {
        return id;
    }
}
