package quikserve.challenge.promotions.dto;

import lombok.Data;
import quikserve.challenge.promotions.model.DiscountType;
import quikserve.challenge.promotions.model.Rule;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class PromotionRequest {

    private String id;

    @NotBlank
    private String name;

    @NotNull
    private Rule rules;

    @NotNull
    private DiscountType discountType;

    @NotNull
    @PositiveOrZero
    private Integer discount;


}
