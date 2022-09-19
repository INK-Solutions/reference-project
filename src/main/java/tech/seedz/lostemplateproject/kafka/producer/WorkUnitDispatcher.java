package tech.seedz.lostemplateproject.kafka.producer;


import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import sample.producer.config.KafkaProducerProperties;
import sample.producer.domain.WorkUnit;

@Log4j2
@Service
public class WorkUnitDispatcher {

    @Autowired
    private KafkaTemplate<String, WorkUnit> workUnitsKafkaTemplate;

    @Autowired
    private KafkaProducerProperties kafkaProducerProperties;

    public boolean dispatch(WorkUnit workUnit) {
        try {
            SendResult<String, WorkUnit> sendResult = workUnitsKafkaTemplate.sendDefault(workUnit.getId(), workUnit).get();
            RecordMetadata recordMetadata = sendResult.getRecordMetadata();
            log.info("topic = {}, partition = {}, offset = {}, workUnit = {}",
                    recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset(), workUnit);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
