package com.software.modsen.ratingservice.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static com.software.modsen.ratingservice.util.ContainersConstants.POSTGRES_DOCKER_IMAGE;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DatabaseContainerConfiguration {

    @Container
    private static final PostgreSQLContainer<?> postgreSQL =
            new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_DOCKER_IMAGE))
                    .withDatabaseName("test_db")
                    .withUsername("test")
                    .withPassword("test");

    static {
        postgreSQL.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQL::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQL::getUsername);
        registry.add("spring.datasource.password", postgreSQL::getPassword);
        registry.add("spring.datasource.driver-class-name", postgreSQL::getDriverClassName);
    }
}
