package qikserve.challenge.checkout.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import qikserve.challenge.checkout.BaseTest;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static qikserve.challenge.checkout.mocks.BasketMock.*;
import static qikserve.challenge.checkout.mocks.ProductsMock.mockProductsSuccess;
import static qikserve.challenge.checkout.mocks.PromotionsMock.mockPromotionsError;
import static qikserve.challenge.checkout.mocks.PromotionsMock.mockPromotionsSuccess;

@RunWith(SpringRunner.class)
public class CheckoutTests extends BaseTest {

    @Test
    public void checkoutBasketWithValidProductsShouldReturnCorrectPriceAndDiscountShouldReturnSuccess() {
        mockProductsSuccess(productsServer);
        mockPromotionsSuccess(promotionsServer);

        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/checkout")
                .body(validBasketWithPromotions())
            .when()
                .post()
            .then()
                .statusCode(200)
                .body("rawValue", equalTo(21.98F))
                .body("totalValue", equalTo(20.93F))
                .body("totalSaved", equalTo(1.05F));
    }

    @Test
    public void checkoutBasketWithInvalidBasketShouldReturnBadRequest() {
        mockProductsSuccess(productsServer);
        mockPromotionsSuccess(promotionsServer);

        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/checkout")
                .body(invalisBasket())
            .when()
                .post()
            .then()
                .statusCode(400)
                .body("error", notNullValue());
    }

    @Test
    public void checkoutBasketWhenNoPromotionsAreApplicableShouldReturnSuccess() {
        mockProductsSuccess(productsServer);
        mockPromotionsSuccess(promotionsServer);

        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/checkout")
                .body(validBasketWithoutPromotions())
            .when()
                .post()
            .then()
                .statusCode(200)
                .body("rawValue", equalTo(1.99F))
                .body("totalValue", equalTo(1.99F))
                .body("totalSaved", equalTo(0));
    }

    @Test
    public void checkoutBasketWhenThePromotionsServiceIsDownShouldReturnSuccess() {
        mockProductsSuccess(productsServer);
        mockPromotionsError(promotionsServer);

        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/checkout")
                .body(validBasketWithPromotions())
            .when()
                .post()
            .then()
                .statusCode(200)
                .body("rawValue", equalTo(21.98F))
                .body("totalValue", equalTo(21.98F))
                .body("totalSaved", equalTo(0));
    }
}
