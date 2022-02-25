package quikserve.challenge.promotions.mocks;

import quikserve.challenge.promotions.model.DiscountType;
import quikserve.challenge.promotions.model.Promotion;

import java.util.List;

public class PromotionStubs {

    public static final String ID = "ABC123";

    public static Promotion validPromotionStub() {
        return Promotion.builder()
                .id(ID)
                .name("Test promo")
                .products(List.of("ABC123"))
                .discountType(DiscountType.PERCENTAGE)
                .discount(5)
                .build();
    }

    public static Promotion invalidPromotionStub() {
        return Promotion.builder()
                .id(ID)
                .name("Test promo")
                .products(List.of("ABC123"))
                .discountType(DiscountType.PERCENTAGE)
                .discount(-5)
                .build();
    }


}
