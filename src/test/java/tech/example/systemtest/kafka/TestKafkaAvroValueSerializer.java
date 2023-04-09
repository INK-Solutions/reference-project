package tech.example.systemtest.kafka;

import house.inksoftware.systemtest.domain.config.infra.kafka.AvroSchemaWithClass;
import house.inksoftware.systemtest.domain.config.infra.kafka.incoming.MockedKafkaAvroValueSerializer;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecordBase;
import tech.allegro.schema.json2avro.converter.JsonAvroConverter;

import java.util.Map;

public class TestKafkaAvroValueSerializer extends MockedKafkaAvroValueSerializer {
    private final Map<String, AvroSchemaWithClass> topicToSchema = SchemaWithClassFactory.create();

    @Override
    public Schema toSchema(String s) {
        return topicToSchema.get(s).getSchema();
    }

    @Override
    public SpecificRecordBase toSpecificRecord(String topic, String json) {
        return new JsonAvroConverter()
                .convertToSpecificRecord(json.getBytes(), topicToSchema.get(topic).getClazz(), toSchema(topic));
    }
}
