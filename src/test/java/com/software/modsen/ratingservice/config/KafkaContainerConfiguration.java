package com.software.modsen.ratingservice.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import static com.software.modsen.ratingservice.util.ContainersConstants.KAFKA_DOCKER_IMAGE;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KafkaContainerConfiguration {
    @Container
    private static final KafkaContainer kafkaContainer =
            new KafkaContainer(DockerImageName.parse(KAFKA_DOCKER_IMAGE));

    static {
        kafkaContainer.start();
    }

    @DynamicPropertySource
    void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }
}
