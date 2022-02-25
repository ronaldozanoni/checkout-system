package quikserve.challenge.promotions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quikserve.challenge.promotions.model.DiscountType;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromotionRequest {

    private String id;

    private String name;

    private List<String> products;

    private DiscountType discountType;

    @PositiveOrZero(message = "Promotions discount value must be 0 or greater")
    private Integer discount;


}
