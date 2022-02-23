package qikserve.challenge.checkout.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Promotion {

    private String id;

    private String name;

    private PromotionRule rules;

    private PromotionDiscountTypes discountType;

    private Integer discount;
}
