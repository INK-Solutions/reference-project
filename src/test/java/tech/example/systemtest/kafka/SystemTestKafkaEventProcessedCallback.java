package tech.example.systemtest.kafka;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Primary;
import tech.example.infrastructure.events.incoming.KafkaEventProcessedCallback;

@TestConfiguration
@Primary
public class SystemTestKafkaEventProcessedCallback extends house.inksoftware.systemtest.domain.config.infra.kafka.incoming.KafkaEventProcessedCallback implements KafkaEventProcessedCallback {

}
