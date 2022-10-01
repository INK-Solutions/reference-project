package tech.seedz.template.service.eventhandler;

import tech.seedz.template.service.event.incoming.IncomingEvent;

public interface IncomingEventHandler<T extends IncomingEvent> {
    void handle(T event);
}
