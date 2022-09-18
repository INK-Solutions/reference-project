package tech.seedz.lostemplateproject.systemtest;

import house.inksoftware.systemtest.db.InitialDataPopulation;
//import house.inksoftware.systemtest.domain.steps.ExecutableStep;
//import house.inksoftware.systemtest.domain.steps.StepExecutorService;
//import house.inksoftware.systemtest.domain.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

public class LosSystemTest extends AbstractSystemTest {
    //private static final String HAPPY_FLOW_SCENARIO_PATH = "tech/seedz/los/systemtest/scenarios/happy-flow/";

    @Autowired
    private DataSource dataSource;

    @Test
    public void launchesApplicationAndExecutesFlyway() {
        populateDatabase();

    }

    private void populateDatabase() {
        new InitialDataPopulation("db", dataSource).populate();
    }

}
