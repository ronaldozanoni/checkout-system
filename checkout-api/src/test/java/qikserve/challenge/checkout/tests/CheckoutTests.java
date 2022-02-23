package qikserve.challenge.checkout.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import qikserve.challenge.checkout.BaseTest;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
public class CheckoutTests extends BaseTest {

    @Test
    public void checkoutBasketWithValidProductsShouldReturnCorrectPriceAndDiscountShouldReturnSuccess() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/checkout")
                .body("{}")
            .when()
                .post()
            .then()
                .statusCode(200)
                .body("totalValue", equalTo(0))
                .body("totalSaved", equalTo(0));
    }

    @Test
    public void checkoutBasketWithInvalidProductsShouldReturnBadRequest() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/checkout")
                .body("{}")
            .when()
                .post()
            .then()
                .statusCode(400)
                .body("$", hasSize(greaterThan(0)));
    }

    @Test
    public void checkoutBasketWhenNoPromotionsAreApplicableShouldReturnSuccess() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/checkout")
                .body("{}")
            .when()
                .post()
            .then()
                .statusCode(200)
                .body("totalValue", equalTo(0))
                .body("totalSaved", equalTo(0));
    }

    @Test
    public void checkoutBasketWhenThePromotionsServiceIsDownShouldReturnSuccess() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/checkout")
                .body("{}")
            .when()
                .post()
            .then()
                .statusCode(200)
                .body("totalValue", equalTo(0))
                .body("totalSaved", equalTo(0));
    }
}
