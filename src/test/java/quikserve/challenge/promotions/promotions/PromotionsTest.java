package quikserve.challenge.promotions.promotions;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import quikserve.challenge.promotions.BaseTest;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class PromotionsTest extends BaseTest {

    @Test
    public void createValidPromotion() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions")
                .body("{}")
            .when()
                .post()
            .then()
                .log().all()
                .statusCode(201)
                .body("id", notNullValue());
    }

    @Test
    public void createInvalidPromotion() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions")
                .body("{}")
            .when()
                .post()
            .then()
                .log().all()
                .statusCode(400)
                .body("errors", not(empty()));
    }

    @Test
    public void getAllPromotions() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions")
            .when()
                .get()
            .then()
                .log().all()
                .statusCode(200)
                .body("$", not(empty()))
                .body("$[1].id", notNullValue());
    }

    @Test
    public void getPromotionsByExistingId() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions/1")
            .when()
                .get()
            .then()
                .log().all()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void getPromotionsByNonExistingId() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions/XYZ999")
            .when()
                .get()
            .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    public void updateValidPromotion() {
        final String id = "1";
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions/" + id)
                .body("{}")
            .when()
                .patch()
            .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(id));
    }

    @Test
    public void updateInvalidPromotion() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions/1")
                .body("{}")
            .when()
                .patch()
            .then()
                .log().all()
                .statusCode(400)
                .body("errors", not(empty()));
    }

    @Test
    public void deleteExistingPromotion() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions/1")
            .when()
                .delete()
            .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void deleteNonExistingPromotion() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions/1")
            .when()
                .delete()
            .then()
                .log().all()
                .statusCode(404);
    }
}

