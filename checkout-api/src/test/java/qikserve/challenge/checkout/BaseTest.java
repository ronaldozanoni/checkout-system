package qikserve.challenge.checkout;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

        productsServer = new WireMockServer(80081);
        promotionsServer = new WireMockServer(80082);
    }

    @AfterEach
    public void cleanUp() {
        promotionsServer.resetMappings();
        productsServer.resetMappings();
    }
}