package qikserve.challenge.checkout.mocks;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


public class ProductsMock {

    public static void mockProductsSuccess(WireMockServer server) {
        server.stubFor(get(urlPathMatching("/products"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[\n" +
                                "  {\n" +
                                "    \"id\": \"Dwt5F7KAhi\",\n" +
                                "    \"name\": \"Amazing Pizza!\",\n" +
                                "    \"price\": 1099\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"id\": \"PWWe3w1SDU\",\n" +
                                "    \"name\": \"Amazing Burger!\",\n" +
                                "    \"price\": 999\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"id\": \"C8GDyLrHJb\",\n" +
                                "    \"name\": \"Amazing Salad!\",\n" +
                                "    \"price\": 499\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"id\": \"4MB7UfpTQs\",\n" +
                                "    \"name\": \"Boring Fries!\",\n" +
                                "    \"price\": 199\n" +
                                "  }\n" +
                                "]")));
    }

    public static void mockProductsError(WireMockServer server) {
        server.stubFor(get(urlPathMatching("/products"))
                .willReturn(aResponse()
                        .withStatus(504)));
    }
}
