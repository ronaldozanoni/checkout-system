package quikserve.challenge.promotions.dto;

import lombok.Data;
import quikserve.challenge.promotions.model.DiscountType;
import quikserve.challenge.promotions.model.Rule;

import javax.validation.constraints.PositiveOrZero;

@Data
public class PromotionRequest {

    private String id;

    private String name;

    private Rule rules;

    private DiscountType discountType;

    @PositiveOrZero(message = "Promotions discount value must be 0 or greater")
    private Integer discount;


}
