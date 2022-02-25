package qikserve.challenge.checkout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import qikserve.challenge.checkout.dto.BasketItemRequest;
import qikserve.challenge.checkout.dto.BasketRequest;
import qikserve.challenge.checkout.dto.CheckoutResponse;
import qikserve.challenge.checkout.dto.ProductResponse;
import qikserve.challenge.checkout.service.CheckoutService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @GetMapping(value = "/products", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponse>> getProducts() {
        List<ProductResponse> products = checkoutService.getProducts();
        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(products);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CheckoutResponse> checkout(@RequestBody @Valid BasketRequest basket) {
        CheckoutResponse checkoutResponse = checkoutService.checkout(basket.getItems());
        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(checkoutResponse);
    }
}
