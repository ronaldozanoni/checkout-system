package qikserve.challenge.checkout.model;

import lombok.Data;

import java.util.List;

@Data
public class PromotionRule {

    private List<String> products;

    private Integer minValue;
}
