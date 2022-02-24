package qikserve.challenge.checkout.mocks;

import qikserve.challenge.checkout.dto.BasketItemRequest;

import java.util.List;

public class BasketMock {

    public static List<BasketItemRequest> validBasketWithPromotions() {
        BasketItemRequest basket = new BasketItemRequest();
        basket.setProductId("Dwt5F7KAhi");
        basket.setQuantity(2);
        return List.of(basket);
    }
    public static List<BasketItemRequest> validBasketWithoutPromotions() {
        BasketItemRequest basket = new BasketItemRequest();
        basket.setProductId("4MB7UfpTQs");
        basket.setQuantity(1);
        return List.of(basket);
    }

    public static List<BasketItemRequest> invalisBasket() {
        BasketItemRequest basket = new BasketItemRequest();
        basket.setProductId("INVALID");
        basket.setQuantity(0);
        return List.of(basket);
    }
}
