package tech.example.infrastructure.events.incoming;

public interface KafkaEventProcessedCallback {
    void handle(String id);
}
