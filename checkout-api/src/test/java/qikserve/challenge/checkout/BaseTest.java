package qikserve.challenge.checkout;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.config.HeaderConfig;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {

    @LocalServerPort
    private int port;

    protected WireMockServer productsServer;

    protected WireMockServer promotionsServer;

    @BeforeAll
    public void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.registerParser("application/json", Parser.JSON);

        productsServer = new WireMockServer(9998);
        productsServer.start();
        promotionsServer = new WireMockServer(9999);
        promotionsServer.start();
    }

    @AfterEach
    public void cleanUp() {
        promotionsServer.resetMappings();
        productsServer.resetMappings();
    }

    @AfterAll
    public void shutdown() {
        productsServer.stop();
        promotionsServer.stop();
    }
}