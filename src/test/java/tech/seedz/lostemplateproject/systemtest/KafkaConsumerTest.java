package tech.seedz.lostemplateproject.systemtest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.io.Resources;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.junit.ClassRule;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.test.utils.KafkaTestUtils;

public class KafkaConsumerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerTest.class);

    public KafkaConsumerTest(int i, boolean b, int i1, String messages) {
    }

    @Test
    public void consumerTest() throws Exception {

        final String topic = "input-topic";

        final TopicPartition topicPartition = new TopicPartition(topic, 0);
        final MockConsumer<String, String> mockConsumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);

        mockConsumer.schedulePollTask(() -> addTopicPartitionsAssignmentAndAddConsumerRecords(topic, mockConsumer, topicPartition));

        final List<String> expectedWords = Arrays.asList("test", "kafka", "event");
        List<String> actualRecords = Files.readAllLines(new File(Resources.getResource("kafka-test.txt").getPath()).toPath());
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



    @ClassRule
    public static KafkaConsumerTest embeddedKafka = new KafkaConsumerTest(2, true, 2, "messages");

    @Test
    public void testSpringKafka() throws Exception {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("sampleConsumer", "false", String.valueOf(embeddedKafka));
        consumerProps.put("auto.offset.reset", "earliest");
        DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        ContainerProperties containerProps = new ContainerProperties("messages");

        final CountDownLatch latch = new CountDownLatch(4);
        containerProps.setMessageListener((AcknowledgingMessageListener<Integer, String>) (message, ack) -> {
            LOGGER.info("Receiving: " + message);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            latch.countDown();
        });
        KafkaMessageListenerContainer<Integer, String> container =
                new KafkaMessageListenerContainer<>(cf, containerProps);
        container.setBeanName("sampleConsumer");


        container.start();
//        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());

        Map<String, Object> senderProps = KafkaTestUtils.producerProps(String.valueOf(embeddedKafka));
        ProducerFactory<Integer, String> pf = new DefaultKafkaProducerFactory<Integer, String>(senderProps);
        KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);
        template.setDefaultTopic("messages");
        template.sendDefault(0, 0, "message1");
        template.sendDefault(0, 1, "message2");
        template.sendDefault(1, 2, "message3");
        template.sendDefault(1, 3, "message4");
        template.flush();
        assertThat(String.valueOf(latch.await(20, TimeUnit.SECONDS)), Boolean.TRUE);
        container.stop();
    }

    @Test
    public void testEmbeddedRawKafka() throws Exception {


        Map<String, Object> senderProps = KafkaTestUtils.producerProps(String.valueOf(embeddedKafka));
        KafkaProducer<Integer, String> producer = new KafkaProducer<>(senderProps);
        producer.send(new ProducerRecord<>("messages", 0, 0, "message0")).get();
        producer.send(new ProducerRecord<>("messages", 0, 1, "message1")).get();
        producer.send(new ProducerRecord<>("messages", 1, 2, "message2")).get();
        producer.send(new ProducerRecord<>("messages", 1, 3, "message3")).get();


        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("sampleRawConsumer", "false", String.valueOf(embeddedKafka));
        consumerProps.put("auto.offset.reset", "earliest");

        final CountDownLatch latch = new CountDownLatch(4);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            KafkaConsumer<Integer, String> kafkaConsumer = new KafkaConsumer<>(consumerProps);
            kafkaConsumer.subscribe(Collections.singletonList("messages"));
            try {
                while (true) {
                    ConsumerRecords<Integer, String> records = kafkaConsumer.poll(100);
                    for (ConsumerRecord<Integer, String> record : records) {
                        LOGGER.info("consuming from topic = {}, partition = {}, offset = {}, key = {}, value = {}",
                                record.topic(), record.partition(), record.offset(), record.key(), record.value());
                        latch.countDown();
                    }
                }
            } finally {
                kafkaConsumer.close();
            }
        });

        assertThat(String.valueOf(latch.await(90, TimeUnit.SECONDS)), Boolean.TRUE);
    }


}