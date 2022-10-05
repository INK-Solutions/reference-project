package tech.seedz.template.systemtest.kafka;

import house.inksoftware.systemtest.domain.config.infra.kafka.AvroSchemaWithClass;
import house.inksoftware.systemtest.domain.config.infra.kafka.outgoing.MockedKafkaAvroValueDeserializer;
import org.apache.avro.Schema;

import java.util.Map;

public class LosKafkaAvroValueDeserializer extends MockedKafkaAvroValueDeserializer {
    private final Map<String, AvroSchemaWithClass> topicToSchema = SchemaWithClassFactory.create();

    @Override
    public Schema toSchema(String s) {
        return topicToSchema.get(s).getSchema();
    }
}
