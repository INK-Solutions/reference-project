package tech.seedz.lostemplateproject.kafka.consumer;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class WorkUnitsConsumer {

    @KafkaListener(topics = "workunits")
    public void onReceiving(WorkUnit workUnit, @Header(KafkaHeaders.OFFSET) Integer offset,
                            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Processing topic = {}, partition = {}, offset = {}, workUnit = {}",
                topic, partition, offset, workUnit);
    }
}
