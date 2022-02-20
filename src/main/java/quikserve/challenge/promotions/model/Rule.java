package quikserve.challenge.promotions.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Rule {

    private List<String> products;

    private BigDecimal minValue;
}
