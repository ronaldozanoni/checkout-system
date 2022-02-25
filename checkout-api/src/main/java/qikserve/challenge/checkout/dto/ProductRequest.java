package qikserve.challenge.checkout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "id must not be null or blank!")
    private String id;

    @NotBlank(message = "name must not be null or blank!")
    private String name;

    @PositiveOrZero(message = "basePrice must be positive or zero!")
    private BigDecimal basePrice;

    @PositiveOrZero(message = "currentPrice must be positive or zero!")
    private BigDecimal currentPrice;

}