package tech.seedz.lostemplateproject.systemtest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;

@Log4
public class KafkaConsumerTest {

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



    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(2, true, 2, "messages");

    @Test
    public void testSpringKafka() throws Exception {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("sampleConsumer", "false", embeddedKafka);
        consumerProps.put("auto.offset.reset", "earliest");
        DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
        ContainerProperties containerProps = new ContainerProperties("messages");

        final CountDownLatch latch = new CountDownLatch(4);
        containerProps.setMessageListener((AcknowledgingMessageListener<Integer, String>) (message, ack) -> {
            log.info("Receiving: " + message);
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

        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
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


        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka);
        KafkaProducer<Integer, String> producer = new KafkaProducer<>(senderProps);
        producer.send(new ProducerRecord<>("messages", 0, 0, "message0")).get();
        producer.send(new ProducerRecord<>("messages", 0, 1, "message1")).get();
        producer.send(new ProducerRecord<>("messages", 1, 2, "message2")).get();
        producer.send(new ProducerRecord<>("messages", 1, 3, "message3")).get();


        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("sampleRawConsumer", "false", embeddedKafka);
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
                        log.info("consuming from topic = {}, partition = {}, offset = {}, key = {}, value = {}",
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