package ru.maren.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.maren.cloudstorage.model.responce.LoginResponse;

import static ru.maren.cloudstorage.data.TestData.LOGIN_REQUEST_1;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class CloudStorageApplicationTests {

    private static final String HOST = "http://localhost:";
    private static final int SERVER_PORT = 8090;
    private static final int DB_PORT = 5432;
    private static final String DB_NAME = "postgres";
    private static final String LOGIN_ENDPOINT = "/login";
    private static final Network NETWORK = Network.newNetwork();

    @Autowired
    private TestRestTemplate restTemplate;


    @Container
    private static PostgreSQLContainer<?> database = new PostgreSQLContainer<>
            ("postgres")
            .withNetwork(NETWORK)
            .withExposedPorts(DB_PORT)
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres");
    @Container
    private static GenericContainer<?> cloudStorage = new GenericContainer<>
            ("cloud-storage-server")
            .withNetwork(NETWORK)
            .withExposedPorts(SERVER_PORT)
            .withEnv("SPRING_DATASOURCE_URL", "jdbc:postgresql://localhost:" + DB_PORT + "/" + DB_NAME)
            .dependsOn(database);


    @Test
    void contextLoads() {
        ResponseEntity<LoginResponse> result = restTemplate.postForEntity(
                HOST + cloudStorage.getMappedPort(SERVER_PORT) + LOGIN_ENDPOINT, LOGIN_REQUEST_1, LoginResponse.class);
        Assertions.assertNotNull(result.getBody());
        Assertions.assertNotNull(result.getBody().getAuthToken());
    }

    @Test
    void contextLoadServer() {
        Assertions.assertTrue(cloudStorage.isRunning());
    }

    @Test
    void contextLoadDatabase() {
        System.out.println(database.getJdbcUrl());
        Assertions.assertTrue(database.isRunning());
    }

}
