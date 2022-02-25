package qikserve.challenge.checkout.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import qikserve.challenge.checkout.dto.ProductResponse;
import qikserve.challenge.checkout.model.Product;

import java.math.BigDecimal;
import java.math.MathContext;

@Slf4j
@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        BeanUtils.copyProperties(product, response);
        response.setBasePrice(BigDecimal.valueOf(product.getPrice()).divide(BigDecimal.valueOf(100), MathContext.DECIMAL128));
        return response;
    }
}
