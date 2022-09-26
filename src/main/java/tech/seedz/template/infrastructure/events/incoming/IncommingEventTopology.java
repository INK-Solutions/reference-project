package tech.seedz.template.infrastructure.events.incoming;

import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

// TODO: WIP
public class IncommingEventTopology {
    @Autowired
    public void process(StreamsBuilder builder) {
        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, String.class);
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, GenericAvroSerde.class);
        streamsConfiguration.put("schema.registry.url", "https://psrc-o268o.eu-central-1.aws.confluent.cloud");
        streamsConfiguration.put("basic.auth.credentials.source", "USER_INFO");
        streamsConfiguration.put("basic.auth.user.info", "XAB5FNTYFVJAXGET:TyHPurDvM55XlFk92lCjgMjeAhpbLmK96vkMTyE6aK1hUl0F97s6gSMbnqOw423u");

        final Map<String, String> serdeConfig = Collections.singletonMap("schema.registry.url", "https://psrc-o268o.eu-central-1.aws.confluent.cloud");
        final Serde<GenericRecord> keyGenericAvroSerde = new GenericAvroSerde();
        keyGenericAvroSerde.configure(serdeConfig, true); // `true` for record keys
        final Serde<GenericRecord> valueGenericAvroSerde = new GenericAvroSerde();
        valueGenericAvroSerde.configure(serdeConfig, false);

        builder.stream("дщфт-фзз", Consumed.with(keyGenericAvroSerde, valueGenericAvroSerde))
                .foreach(new ForeachAction<GenericRecord, GenericRecord>() {
                    @Override
                    public void apply(GenericRecord key, GenericRecord value) {

                    }
                });
    }
}
