package tech.seedz.template.service.event.outgoing;

public interface EventDispatcher {
    void dispatch(PublishableEvent event);
}
