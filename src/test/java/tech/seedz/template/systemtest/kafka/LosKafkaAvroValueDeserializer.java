package tech.seedz.template.systemtest.kafka;

import house.inksoftware.systemtest.domain.config.infra.kafka.MockedKafkaAvroValueDeserializer;
import org.apache.avro.Schema;
import tech.seedz.template.generated.loan.LoanApprovalRequest;

import java.util.Map;

public class LosKafkaAvroValueDeserializer extends MockedKafkaAvroValueDeserializer {
    private final Map<String, Schema> topicToSchema;

    public LosKafkaAvroValueDeserializer(Map<String, Schema> topicToSchema) {
        this.topicToSchema = topicToSchema;
    }


    @Override
    public Schema toSchema(String s) {
        return LoanApprovalRequest.SCHEMA$;
    }
}
