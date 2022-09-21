package tech.seedz.lostemplateproject.controller.config.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.seedz.lostemplateproject.kafka.producer.WorkUnit;
import tech.seedz.lostemplateproject.kafka.producer.WorkUnitDispatcher;

@RestController
public class SampleKafkaMessageController {

    @Autowired
    private WorkUnitDispatcher workUnitDispatcher;

    @GetMapping("/kafka-message")
    public boolean sendMessage(WorkUnit workUnit) {
        return this.workUnitDispatcher.dispatch(workUnit);
    }
}
