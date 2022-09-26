package tech.seedz.template.systemtest;

import house.inksoftware.systemtest.db.InitialDataPopulation;
//import house.inksoftware.systemtest.domain.steps.ExecutableStep;
//import house.inksoftware.systemtest.domain.steps.StepExecutorService;
//import house.inksoftware.systemtest.domain.utils.JsonUtils;
import house.inksoftware.systemtest.domain.steps.ExecutableStep;
import house.inksoftware.systemtest.domain.steps.StepExecutorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

public class DefaultSystemTest extends AbstractSystemTest {
    private static final String HAPPY_FLOW_SCENARIO_PATH = "tech/seedz/template/scenarios/happy-flow/";

    @Autowired
    private DataSource dataSource;

    @Test
    public void testBusinessLogic() {
        var testExecutionContext = new DefaultTestExecutionContext();
        StepExecutorService stepExecutorService = new StepExecutorService(HAPPY_FLOW_SCENARIO_PATH, getPort(), testExecutionContext, getRestTemplate());

        ExecutableStep step = ExecutableStep.builder("1-create-loan").build();
        stepExecutorService.execute(step);
    }
}
