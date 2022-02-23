package quikserve.challenge.promotions.tests;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import quikserve.challenge.promotions.BaseTest;
import quikserve.challenge.promotions.model.Promotion;

import java.io.File;

import static org.hamcrest.Matchers.*;
import static quikserve.challenge.promotions.mocks.PromotionStubs.*;

@RunWith(SpringRunner.class)
public class PromotionsTest extends BaseTest {


    private File validInputJson;
    private File invalidInputJson;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;


    @BeforeAll
    public void setup() {
        validInputJson = new File(getClass().getClassLoader().getResource("mocks/valid_promotion_request.json").getFile());
        invalidInputJson = new File(getClass().getClassLoader().getResource("mocks/invalid_promotion_request.json").getFile());
    }

    @BeforeEach
    public void setupEach() {
        Promotion promotion = validPromotionStub();
        mongoTemplate.save(promotion).subscribe();
    }

    @AfterEach
    public void cleanUp() {
        mongoTemplate.dropCollection(Promotion.class).subscribe();
    }


    @Test
    public void createValidPromotion() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .basePath("/promotions")
                .body(validInputJson)
            .when()
                .post()
            .then()
                .log().all()
                .statusCode(201)
                .header("location", notNullValue());
    }

    @Test
    public void createInvalidPromotion() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions")
                .body(invalidInputJson)
            .when()
                .post()
            .then()
                .log().all()
                .statusCode(400)
                .body("$", hasSize(greaterThan(0)));
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
                .body("$", hasSize(1));
    }

    @Test
    public void getPromotionsByExistingId() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions/")
            .when()
                .get(ID)
            .then()
                .log().headers()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void getPromotionsByNonExistingId() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions/")
            .when()
                .get("XYZ789")
            .then()
                .log().headers()
                .statusCode(404);
    }

    @Test
    public void partialUpdateValidPromotion() {
        Promotion promo = Promotion.builder().name("New name").build();

        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions/")
                .body(promo)
            .when()
                .patch(ID)
            .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(ID))
                .body("name", equalTo(promo.getName()));
    }

    @Test
    public void partialUpdateInvalidPromotion() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions/")
                .body(invalidInputJson)
            .when()
                .patch(ID)
            .then()
                .log().all()
                .statusCode(400)
                .body("$", not(empty()));
    }

    @Test
    public void fullUpdateValidPromotion() {
        Promotion promo = validPromotionStub();
        promo.setName("New name");

        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions")
                .body(promo)
            .when()
                .put()
            .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(ID))
                .body("name", equalTo(promo.getName()));
    }

    @Test
    public void fullUpdateInvalidPromotion() {
        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions/")
                .body(invalidInputJson)
            .when()
                .put()
            .then()
                .log().all()
                .statusCode(400);
    }

    @Test
    public void deleteExistingPromotion() {
        Promotion promo = validPromotionStub();

        RestAssured
            .given()
                .contentType(ContentType.JSON)
                .basePath("/promotions")
                .body(promo)
            .when()
                .delete()
            .then()
                .log().all()
                .statusCode(204);
    }

}

