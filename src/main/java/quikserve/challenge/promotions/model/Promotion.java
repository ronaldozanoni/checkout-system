package quikserve.challenge.promotions.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;

@Data
@Document(collection = "promotion")
public class Promotion {

    @MongoId
    private String id;

    private String name;

    private Rule rules;

    private DiscountType discountType;

    private BigDecimal discount;
}
