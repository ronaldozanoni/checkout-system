package quikserve.challenge.promotions;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    private int port;

    @BeforeAll
    public void setUp() {
        RestAssured.port = port;
    }

}
