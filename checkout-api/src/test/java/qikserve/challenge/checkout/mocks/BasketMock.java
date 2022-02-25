package qikserve.challenge.checkout.mocks;

import qikserve.challenge.checkout.dto.BasketItemRequest;
import qikserve.challenge.checkout.dto.ProductRequest;

import java.math.BigDecimal;
import java.util.List;

public class BasketMock {

    public static List<BasketItemRequest> validBasketWithPromotions() {
        BasketItemRequest basket = new BasketItemRequest();
        basket.setProduct(ProductRequest.builder()
                .id("Dwt5F7KAhi")
                .name("Amazing Pizza!")
                .basePrice(BigDecimal.valueOf(10.99))
                .currentPrice(BigDecimal.valueOf(9.89))
                .build());
        basket.setQuantity(1);
        return List.of(basket);
    }
    public static List<BasketItemRequest> validBasketWithoutPromotions() {
        BasketItemRequest basket = new BasketItemRequest();
        basket.setProduct(ProductRequest.builder()
                .id("Dwt5F7KAhi")
                .name("Amazing Pizza!")
                .basePrice(BigDecimal.valueOf(10.99))
                .currentPrice(BigDecimal.valueOf(10.99))
                .build());
        basket.setQuantity(1);
        return List.of(basket);
    }

    public static List<BasketItemRequest> invalisBasket() {
        BasketItemRequest basket = new BasketItemRequest();
        basket.setProduct(null);
        basket.setQuantity(0);
        return List.of(basket);
    }
}
