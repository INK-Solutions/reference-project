package tech.seedz.lostemplateproject.systemtest;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import tech.seedz.lostemplateproject.LosTemplateProjectApplication;
import tech.seedz.lostemplateproject.systemtest.db.LosPostgresqlTestContainer;


@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LosTemplateProjectApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({LosTemplateProjectApplication.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("systemtest")
public abstract class AbstractSystemTest {
    private static final PostgreSQLContainer CONTAINER = LosPostgresqlTestContainer.initialise();

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    public TestRestTemplate getRestTemplate() {
        return restTemplate;
    }

    public int getPort() {
        return port;
    }
}
