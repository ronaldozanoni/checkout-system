package qikserve.challenge.checkout.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import qikserve.challenge.checkout.BaseTest;
import qikserve.challenge.checkout.dto.BasketRequest;

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
                .body(new BasketRequest(validBasketWithPromotions()))
            .when()
                .post()
            .then()
                .statusCode(200)
                .body("rawValue", equalTo(10.99F))
                .body("totalValue", equalTo(9.89F))
                .body("totalSaved", equalTo(1.10F));
    }

    @Test
    public void checkoutBasketWithInvalidBasketShouldReturnBadRequest() {
        mockProductsSuccess(productsServer);
        mockPromotionsSuccess(promotionsServer);

        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/checkout")
                .body(new BasketRequest(invalisBasket()))
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
                .body(new BasketRequest(validBasketWithoutPromotions()))
            .when()
                .post()
            .then()
                .statusCode(200)
                .body("rawValue", equalTo(10.99F))
                .body("totalValue", equalTo(10.99F))
                .body("totalSaved", equalTo(0.00F));
    }

    @Test
    public void getProductsPromotions() {
        mockProductsSuccess(productsServer);
        mockPromotionsSuccess(promotionsServer);

        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/checkout/products")
            .when()
                .get()
            .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)));
    }

    @Test
    public void getProductsWhenPromotionsServerIsDownShouldRecoverAndReturnNormally() {
        mockProductsSuccess(productsServer);
        mockPromotionsError(promotionsServer);

        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/checkout/products")
            .when()
                .get()
            .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)));
    }
}
