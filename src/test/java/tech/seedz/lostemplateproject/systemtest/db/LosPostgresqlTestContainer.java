package tech.seedz.lostemplateproject.systemtest.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class LosPostgresqlTestContainer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LosPostgresqlTestContainer.class);

    private static final String IMAGE_VERSION = "kartoza/postgis:12.1";
    private static PostgreSQLContainer container;

    public static PostgreSQLContainer initialise() {
        if (container == null) {
            DockerImageName myImage = DockerImageName.parse(IMAGE_VERSION).asCompatibleSubstituteFor("postgres");
            container = new PostgreSQLContainer(myImage);
            container.start();
            System.setProperty("DB_URL", container.getJdbcUrl());
            System.setProperty("DB_USERNAME", container.getUsername());
            System.setProperty("DB_PASSWORD", container.getPassword());
            LOGGER.info(
                    "Starting test database... jdbc: {}, user: {}, password: {}",
                    container.getJdbcUrl(), container.getUsername(), container.getPassword()
            );
        }
        return container;
    }

    public static void shutdown() {
        if (container == null) {
            LOGGER.info("Shutting down test database...");
            container.stop();
            container.close();
        }
    }
}

