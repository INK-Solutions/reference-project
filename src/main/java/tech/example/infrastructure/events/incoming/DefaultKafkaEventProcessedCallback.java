package tech.example.infrastructure.events.incoming;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class DefaultKafkaEventProcessedCallback implements KafkaEventProcessedCallback {


    @Override
    public void handle(String id) {
        log.info("Event with id {} is processed", id);
    }
}
