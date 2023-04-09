package tech.example.service.event.handler;

import tech.example.service.event.incoming.IncomingEvent;

public interface IncomingEventHandler<T extends IncomingEvent> {
    void handle(T event);
}
