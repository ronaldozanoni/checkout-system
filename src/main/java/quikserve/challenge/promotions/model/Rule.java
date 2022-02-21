package quikserve.challenge.promotions.model;

import lombok.Data;

import java.util.List;

@Data
public class Rule {

    private List<String> products;

    private Integer minValue;
}
