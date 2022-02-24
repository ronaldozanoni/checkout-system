package qikserve.challenge.checkout.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qikserve.challenge.checkout.dto.BasketItemRequest;
import qikserve.challenge.checkout.dto.CheckoutResponse;
import qikserve.challenge.checkout.service.CheckoutService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CheckoutResponse> checkout(@RequestBody List<BasketItemRequest> basket) {
        CheckoutResponse checkoutResponse = checkoutService.checkout(basket);
        return ResponseEntity.ok()
                .contentType(APPLICATION_JSON)
                .body(checkoutResponse);
    }
}
