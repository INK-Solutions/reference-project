package tech.seedz.lostemplateproject.controller.config;

import org.apache.kafka.clients.admin.NewTopic;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Value("${kafka.topic.name}")
    private String topic;

    @Bean
    public NewTopic newTopic() {
        NewTopic newTopic = new NewTopic(topic, 1, (short) 1);

        return newTopic;
    }
}
