package tech.seedz.template.systemtest;

import house.inksoftware.systemtest.AbstractSystemTest;
import house.inksoftware.systemtest.domain.SystemTestExecutionServiceFactory;
import house.inksoftware.systemtest.domain.config.SystemTestConfiguration;
import house.inksoftware.systemtest.domain.config.infra.db.postgres.SystemTestPostgresLauncher;
import house.inksoftware.systemtest.domain.config.infra.kafka.KafkaConfigurationFactory;
import house.inksoftware.systemtest.domain.config.infra.rest.RestConfigurationFactory;
import house.inksoftware.systemtest.domain.steps.request.RequestStep;
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
import tech.seedz.template.TemplateApplication;
import tech.seedz.template.systemtest.kafka.LosKafkaAvroValueDeserializer;
import tech.seedz.template.systemtest.kafka.LosKafkaAvroValueDeserializerFactory;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TemplateApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(value = {DefaultLostTemplateSystemTest.ResourceLauncher.class}, mergeMode = MERGE_WITH_DEFAULTS)
@Import({TemplateApplication.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("systemtest")
@EmbeddedKafka
public class DefaultLostTemplateSystemTest extends AbstractSystemTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private LosKafkaAvroValueDeserializerFactory losKafkaAvroValueDeserializerFactory;

    private static final String HAPPY_FLOW_SCENARIO_PATH = "tech/seedz/template/scenarios/happy-flow/";

    @Test
    public void testBusinessLogic() throws Exception {
        var config = SystemTestConfiguration.builder()
                .baseFolder(HAPPY_FLOW_SCENARIO_PATH)
                .restConfiguration(RestConfigurationFactory.create(restTemplate, port))
                .kafkaConfiguration(KafkaConfigurationFactory.create(HAPPY_FLOW_SCENARIO_PATH, embeddedKafkaBroker, losKafkaAvroValueDeserializerFactory.create()))
                .build();
        var systemTestExecutionService = SystemTestExecutionServiceFactory.create(config);


        RequestStep step = RequestStep.builder("1-create-loan").build();
        systemTestExecutionService.execute(step);
    }


    public static class ResourceLauncher implements TestExecutionListener {
        private final SystemTestPostgresLauncher dbLauncher = new SystemTestPostgresLauncher("postgres:11.1");
        @Override
        public void beforeTestClass(TestContext testContext) {
            dbLauncher.setup();
//            new SystemTestDatabasePopulatorLauncher("db/migration/table", testContext.getApplicationContext().getBean(DataSource.class)).setup();
        }

        @Override
        public void afterTestExecution(TestContext testContext) throws Exception {
//            dbLauncher.shutdown();
//            kafkaLauncher.shutdown();
        }
    }
}
