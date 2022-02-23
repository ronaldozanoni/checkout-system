package quikserve.challenge.promotions.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Promotion {

    @Id
    private String id;

    private String name;

    private Rule rules;

    private DiscountType discountType;

    private Integer discount;
}
