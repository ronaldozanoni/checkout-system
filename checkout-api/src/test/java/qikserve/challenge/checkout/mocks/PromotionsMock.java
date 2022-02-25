package qikserve.challenge.checkout.mocks;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class PromotionsMock {

    public static void mockPromotionsSuccess(WireMockServer server) {
        server.stubFor(get(urlPathMatching("/promotions"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[\n" +
                                "  {\n" +
                                "    \"id\": \"6215ade1590a7706238d9ba9\",\n" +
                                "    \"name\": \"Sad Sunday!\",\n" +
                                "    \"products\": [\"Dwt5F7KAhi\"],\n" +
                                "    \"discountType\": \"PERCENTAGE\",\n" +
                                "    \"discount\": 5\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"id\": \"62177a14f1de4e3cdc3d4717\",\n" +
                                "    \"name\": \"Sad Sunday!\",\n" +
                                "    \"products\": [\n" +
                                "        \"PWWe3w1SDU\"\n" +
                                "      ],\n" +
                                "    \"discountType\": \"FIXED_VALUE\",\n" +
                                "    \"discount\": 200\n" +
                                "  }\n" +
                                "]")));
    }

    public static void mockPromotionsError(WireMockServer server) {
        server.stubFor(get(urlPathMatching("/promotions"))
                .willReturn(aResponse()
                        .withStatus(504)));
    }
}
