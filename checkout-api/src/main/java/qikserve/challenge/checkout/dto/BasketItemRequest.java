package qikserve.challenge.checkout.dto;

import lombok.Data;

@Data
public class BasketItemRequest {

    private String productId;

    private Integer quantity;

}
