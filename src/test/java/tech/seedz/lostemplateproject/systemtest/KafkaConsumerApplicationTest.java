package tech.seedz.lostemplateproject.systemtest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;


public class KafkaConsumerApplicationTest {

    private final static String TEST_CONFIG_FILE = "configuration/test.properties";

    @Test
    public void consumerTest() throws Exception {

        final String topic = testConsumerProps.getProperty("kafka.topic.name");
        final TopicPartition topicPartition = new TopicPartition(topic, 0);
        final MockConsumer<String, String> mockConsumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);

        mockConsumer.schedulePollTask(() -> addTopicPartitionsAssignmentAndAddConsumerRecords(topic, mockConsumer, topicPartition));
        mockConsumer.schedulePollTask(consumerApplication::shutdown);
        consumerApplication.runConsume(testConsumerProps);

        final List<String> expectedWords = Arrays.asList("test", "kafka", "event");
        List<String> actualRecords = Files.readAllLines(tempFilePath);
        assertThat(actualRecords, equalTo(expectedWords));
    }

    private void addTopicPartitionsAssignmentAndAddConsumerRecords(final String topic,
                                                                   final MockConsumer<String, String> mockConsumer,
                                                                   final TopicPartition topicPartition) {

        final Map<TopicPartition, Long> beginningOffsets = new HashMap<>();
        beginningOffsets.put(topicPartition, 0L);
        mockConsumer.rebalance(Collections.singletonList(topicPartition));
        mockConsumer.updateBeginningOffsets(beginningOffsets);
        addConsumerRecords(mockConsumer,topic);
    }

    private void addConsumerRecords(final MockConsumer<String, String> mockConsumer, final String topic) {
        mockConsumer.addRecord(new ConsumerRecord<>(topic, 0, 0, null, "test"));
        mockConsumer.addRecord(new ConsumerRecord<>(topic, 0, 1, null, "kafka"));
        mockConsumer.addRecord(new ConsumerRecord<>(topic, 0, 2, null, "event"));
    }


}