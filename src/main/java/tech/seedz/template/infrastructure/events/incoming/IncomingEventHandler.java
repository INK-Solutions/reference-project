package tech.seedz.template.infrastructure.events.incoming;

import tech.seedz.template.service.event.incoming.IncomingEvent;

public interface IncomingEventHandler<T extends IncomingEvent> {
    void handle(T event);
}
