package quikserve.challenge.promotions.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Promotion {

    private String id;

    private String name;

    private Rule rules;

    private DiscountType discountType;

    private Integer discount;
}
