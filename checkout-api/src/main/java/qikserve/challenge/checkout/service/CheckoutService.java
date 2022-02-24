package qikserve.challenge.checkout.service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import qikserve.challenge.checkout.client.ProductsAPIClient;
import qikserve.challenge.checkout.client.PromotionsAPIClient;
import qikserve.challenge.checkout.dto.BasketItemRequest;
import qikserve.challenge.checkout.dto.CheckoutResponse;
import qikserve.challenge.checkout.exception.InvalidProductIdException;
import qikserve.challenge.checkout.model.Product;
import qikserve.challenge.checkout.model.Promotion;
import qikserve.challenge.checkout.model.PromotionDiscountTypes;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CheckoutService {

    @Autowired
    private ProductsAPIClient productsClient;

    @Autowired
    private PromotionsAPIClient promotionsClient;

    public CheckoutResponse checkout(List<BasketItemRequest> basket) {
        List<Product> products = productsClient.getProducts();
        log.info("msg=Success fetching products! Found {} products", products.size());

        // Now for each product, we get the value out of the list and calculate it's value depending on the quantity
        Map<String, Integer> basketValues = basket.stream()
                .collect(Collectors.toMap(BasketItemRequest::getProductId,
                        basketItem -> calculateItemValue(products, basketItem)));

        // Now we already have the raw value
        final Integer rawValue = sumBasketValues(basketValues);

        // Off to the promotions now.
        List<Promotion> promotions = getPromotions();

        // We're going to split the promotions into two groups: product based and value based ones.
        List<Promotion> productBasedPromotions = filterPromotions(promotions, this::isProductBasedPromotion);
        List<Promotion> valueBasedPromotions = filterPromotions(promotions, promo -> !isProductBasedPromotion(promo));

        // We can apply product based promotions this way:
        // For each product in basket, if we find a promotion that applies to it, then we apply the promo to that product
        basketValues.keySet()
                .forEach(productId -> applyPromotionsToProduct(basketValues, productBasedPromotions, productId));

        // Finally, the value based promotions
        // Here we are going to apply them over the raw value we calculated earlier
        Integer totalDiscounted = calculateTotalDiscounted(rawValue, valueBasedPromotions);
        Integer newBasketValue = sumBasketValues(basketValues);

        // And we get the two remaining values!
        final Integer totalValue =  newBasketValue - totalDiscounted;
        final Integer totalSaved = rawValue - totalValue;

        return CheckoutResponse.builder()
                .rawValue(BigDecimal.valueOf(rawValue).divide(BigDecimal.valueOf(100)))
                .totalValue(BigDecimal.valueOf(totalValue).divide(BigDecimal.valueOf(100)))
                .totalSaved(BigDecimal.valueOf(totalSaved).divide(BigDecimal.valueOf(100)))
                .build();

    }

    private List<Promotion> getPromotions() {
        // Hystrix at home
        List<Promotion> promotions;
        try {
            promotions = promotionsClient.getPromotions();
            log.info("Success fetching promotions! Found {} active promotions", promotions.size());
        } catch (FeignException err) {
            log.error("msg=Error fetching promotions", err);
            promotions = Collections.emptyList();
        }
        return promotions;
    }

    private int calculateItemValue(List<Product> products, BasketItemRequest basketItem) {
        Integer unitPrice = getPrice(products, basketItem.getProductId());
        return unitPrice * basketItem.getQuantity();
    }

    private Integer getPrice(List<Product> products, String productId) {
        return products.stream()
                .filter(product -> Objects.nonNull(product) && Objects.nonNull(productId) && productId.equals(product.getId()))
                .findAny()
                .orElseThrow(() -> new InvalidProductIdException("Product with ID=" + productId + " was not found"))
                .getPrice();
    }

    private Integer sumBasketValues(Map<String, Integer> basketValues) {
        return basketValues.values()
                .stream()
                .reduce(0, Integer::sum);
    }

    private Integer calculateDiscount(Integer total, Promotion promotion) {
        if (PromotionDiscountTypes.FIXED_VALUE.equals(promotion.getDiscountType())) {
            return promotion.getDiscount();
        } else if(PromotionDiscountTypes.PERCENTAGE.equals(promotion.getDiscountType())) {
            return (total / 100) * promotion.getDiscount();
        }
        return 0;
    }

    private boolean isProductBasedPromotion(Promotion promotion) {
        return Objects.nonNull(promotion)
                && Objects.nonNull(promotion.getRules())
                && Objects.nonNull(promotion.getRules().getProducts())
                && !promotion.getRules().getProducts().isEmpty();
    }

    private void applyPromotionsToProduct(Map<String, Integer> basketValues, List<Promotion> productBasedPromotions, String productId) {
        productBasedPromotions.stream()
                .filter(promo -> promo.getRules().getProducts().contains(productId))
                .forEach(promotion -> basketValues.put(productId,
                        basketValues.get(productId) - calculateDiscount(basketValues.get(productId), promotion)));
    }

    private List<Promotion> filterPromotions(List<Promotion> promotions, Predicate<Promotion> testFilter) {
        return promotions
                .stream()
                .filter(testFilter::test)
                .collect(Collectors.toList());
    }

    private Integer calculateTotalDiscounted(Integer rawValue, List<Promotion> valueBasedPromotions) {
        return valueBasedPromotions.stream()
                .filter(promo -> Objects.nonNull(promo) && Objects.nonNull(promo.getRules()) && rawValue > promo.getRules().getMinValue())
                .map(promo -> calculateDiscount(rawValue, promo))
                .reduce(0, Integer::sum);
    }
}
