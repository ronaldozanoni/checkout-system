package qikserve.challenge.checkout.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import qikserve.challenge.checkout.model.Promotion;

import java.util.List;

@FeignClient("promotions")
public interface PromotionsAPIClient {

    @GetMapping(value = "/promotions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<Promotion> getPromotions();

}
