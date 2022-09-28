package tech.seedz.template.systemtest;

import house.inksoftware.systemtest.AbstractSystemTest;
import house.inksoftware.systemtest.configuration.infrastructure.db.SystemTestDatabasePopulatorLauncher;
import house.inksoftware.systemtest.configuration.infrastructure.db.postgres.SystemTestPostgresLauncher;
import house.inksoftware.systemtest.configuration.infrastructure.kafka.SystemTestKafkaLauncher;
import house.inksoftware.systemtest.domain.steps.ExecutableStep;
import house.inksoftware.systemtest.domain.steps.StepExecutorService;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.SpringRunner;
import tech.seedz.template.TemplateApplication;

import javax.sql.DataSource;
import java.util.Arrays;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TemplateApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(value = {DefaultLostTemplateSystemTest.ResourceLauncher.class}, mergeMode = MERGE_WITH_DEFAULTS)
@Import({TemplateApplication.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("systemtest")
public class DefaultLostTemplateSystemTest extends AbstractSystemTest {

    private static final String HAPPY_FLOW_SCENARIO_PATH = "tech/seedz/template/scenarios/happy-flow/";

    @Test
    public void testBusinessLogic() {
        var testExecutionContext = new DefaultTestExecutionContext();
        StepExecutorService stepExecutorService = new StepExecutorService(HAPPY_FLOW_SCENARIO_PATH, port, testExecutionContext, restTemplate);

        ExecutableStep step = ExecutableStep.builder("1-create-loan").build();
        stepExecutorService.execute(step);
    }


    public static class ResourceLauncher implements TestExecutionListener {
        @Override
        public void beforeTestClass(TestContext testContext) {
            new SystemTestPostgresLauncher("postgres:11.1").setup();
            new SystemTestKafkaLauncher("confluentinc/cp-kafka:5.4.3").setup();
            new SystemTestDatabasePopulatorLauncher("db/migration/table", testContext.getApplicationContext().getBean(DataSource.class)).setup();
        }
    }
}
