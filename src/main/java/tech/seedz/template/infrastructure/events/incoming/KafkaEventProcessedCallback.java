package tech.seedz.template.infrastructure.events.incoming;

public interface KafkaEventProcessedCallback {
    void handle(String id);
}
