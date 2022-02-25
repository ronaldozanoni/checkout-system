package qikserve.challenge.checkout.service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qikserve.challenge.checkout.client.ProductsAPIClient;
import qikserve.challenge.checkout.client.PromotionsAPIClient;
import qikserve.challenge.checkout.dto.BasketItemRequest;
import qikserve.challenge.checkout.dto.CheckoutResponse;
import qikserve.challenge.checkout.dto.ProductResponse;
import qikserve.challenge.checkout.mapper.ProductMapper;
import qikserve.challenge.checkout.model.Product;
import qikserve.challenge.checkout.model.Promotion;
import qikserve.challenge.checkout.model.PromotionDiscountTypes;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CheckoutService {

    @Autowired
    private ProductsAPIClient productsClient;

    @Autowired
    private PromotionsAPIClient promotionsClient;

    @Autowired
    private ProductMapper productMapper;

    public List<ProductResponse> getProducts() {
        List<Product> products = productsClient.getProducts();
        log.info("msg=Success fetching products! Found {} products", products.size());

        List<Promotion> promotions = getPromotions();
        return products.stream()
                .map(product -> {
                    List<Promotion> activePromotions = promotions.stream()
                            .filter(promo -> Objects.nonNull(promo.getProducts()) && promo.getProducts().contains(product.getId()))
                            .collect(Collectors.toList());
                    BigDecimal newPrice = BigDecimal.valueOf(product.getPrice());
                    for (Promotion promotion : activePromotions) {
                        newPrice = newPrice.subtract(calculateDiscount(product.getPrice(), promotion));
                    }
                    ProductResponse response = productMapper.toResponse(product);
                    response.setCurrentPrice(newPrice.divide(BigDecimal.valueOf(100), new MathContext(3, RoundingMode.HALF_UP)));
                    return response;
                })
                .collect(Collectors.toList());
    }

    public CheckoutResponse checkout(List<BasketItemRequest> basket) {
        final BigDecimal rawValue = basket.stream()
                .map(basketItem -> basketItem.getProduct().getBasePrice().multiply(BigDecimal.valueOf(basketItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final BigDecimal totalValue = basket.stream()
                .map(basketItem -> basketItem.getProduct().getCurrentPrice().multiply(BigDecimal.valueOf(basketItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final BigDecimal totalSaved = rawValue.subtract(totalValue);

        return CheckoutResponse.builder()
                .rawValue(rawValue)
                .totalValue(totalValue)
                .totalSaved(totalSaved)
                .build();
    }

    private List<Promotion> getPromotions() {
        // RIP Hystrix :(
        List<Promotion> promotions;
        try {
            promotions = promotionsClient.getPromotions();
            log.info("msg=Success fetching promotions! Found {} active promotions. promos={}", promotions.size(), promotions);
        } catch (FeignException err) {
            log.error("msg=Error fetching promotions", err);
            promotions = Collections.emptyList();
        }
        return promotions;
    }

    private BigDecimal calculateDiscount(Integer total, Promotion promotion) {
        if (PromotionDiscountTypes.FIXED_VALUE.equals(promotion.getDiscountType())) {
            return BigDecimal.valueOf(promotion.getDiscount());
        } else if(PromotionDiscountTypes.PERCENTAGE.equals(promotion.getDiscountType())) {
            final BigDecimal discountRate = BigDecimal.valueOf(promotion.getDiscount()).divide(BigDecimal.valueOf(100), MathContext.DECIMAL64);
            return BigDecimal.valueOf(total).multiply(discountRate);
        }
        return BigDecimal.ZERO;
    }
}
