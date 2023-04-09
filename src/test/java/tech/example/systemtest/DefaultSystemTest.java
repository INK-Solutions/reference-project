package tech.example.systemtest;

import house.inksoftware.systemtest.AbstractSystemTest;
import house.inksoftware.systemtest.domain.SystemTestExecutionServiceFactory;
import house.inksoftware.systemtest.domain.config.SystemTestConfiguration;
import house.inksoftware.systemtest.domain.config.infra.db.postgres.SystemTestPostgresLauncher;
import house.inksoftware.systemtest.domain.config.infra.kafka.KafkaConfigurationFactory;
import house.inksoftware.systemtest.domain.config.infra.rest.RestConfigurationFactory;
import house.inksoftware.systemtest.domain.steps.request.RequestStep;
import house.inksoftware.systemtest.domain.utils.JsonUtils;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.SpringRunner;
import tech.example.systemtest.kafka.TestKafkaAvroValueDeserializer;
import tech.example.systemtest.kafka.TestKafkaAvroValueSerializer;
import tech.example.systemtest.kafka.SystemTestKafkaEventProcessedCallback;
import tech.example.TemplateApplication;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TemplateApplication.class, SystemTestKafkaEventProcessedCallback.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(value = {DefaultSystemTest.ResourceLauncher.class}, mergeMode = MERGE_WITH_DEFAULTS)
@Import({TemplateApplication.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("systemtest")
@EmbeddedKafka
public class DefaultSystemTest extends AbstractSystemTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private SystemTestKafkaEventProcessedCallback kafkaEventProcessedCallback;

    private static final String HAPPY_FLOW_SCENARIO_PATH = "tech/example/scenarios/happy-flow/";

    @Test
    public void testBusinessLogic() throws Exception {
        var testExecutionContext = new TestExecutionContext();

        var config = configure();
        var service = SystemTestExecutionServiceFactory.create(config);


        RequestStep step = RequestStep.builder("1-create-loan")
                .callbackFunction(response -> testExecutionContext.setLoanId(response.read("id")))
                .build();
        service.execute(step);

         step = RequestStep.builder("2-receive-decision")
                 .requestPlaceholders(JsonUtils.JsonRequestPlaceholder.of("loanId", testExecutionContext.getLoanId()))
                 .build();
        service.execute(step);

         step = RequestStep.builder("3-query-loan")
                 .requestPlaceholders(JsonUtils.JsonRequestPlaceholder.of("loanId", testExecutionContext.getLoanId()))
                 .build();
        service.execute(step);
    }

    private SystemTestConfiguration configure() {
        return SystemTestConfiguration.builder()
                .baseFolder(HAPPY_FLOW_SCENARIO_PATH)
                .restConfiguration(RestConfigurationFactory.create(restTemplate, port))
                .kafkaConfiguration(
                        KafkaConfigurationFactory.create(HAPPY_FLOW_SCENARIO_PATH, embeddedKafkaBroker,
                                new TestKafkaAvroValueSerializer(), new TestKafkaAvroValueDeserializer(), kafkaEventProcessedCallback)
                )
                .build();
    }


    public static class ResourceLauncher implements TestExecutionListener {
        private final SystemTestPostgresLauncher dbLauncher = new SystemTestPostgresLauncher("postgres:11.1");
        @Override
        public void beforeTestClass(TestContext testContext) {
            dbLauncher.setup();
        }

        @Override
        public void afterTestExecution(TestContext testContext) throws Exception {
            dbLauncher.shutdown();
        }
    }

    public class TestExecutionContext {
        private String loanId;

        public String getLoanId() {
            return loanId;
        }

        public void setLoanId(String loanId) {
            this.loanId = loanId;
        }
    }
}
