package qikserve.challenge.checkout.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CheckoutResponse {

    private BigDecimal rawValue;

    private BigDecimal totalSaved;

    private BigDecimal totalValue;
}
