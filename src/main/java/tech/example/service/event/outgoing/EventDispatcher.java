package tech.example.service.event.outgoing;

public interface EventDispatcher {
    void dispatch(OutgoingEvent event);
}
