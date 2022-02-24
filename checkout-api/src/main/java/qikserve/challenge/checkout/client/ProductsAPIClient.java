package qikserve.challenge.checkout.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import qikserve.challenge.checkout.model.Product;

import java.util.List;

@Component
@FeignClient(value = "products", url = "${feign.client.config.products.url}")
public interface ProductsAPIClient {

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Product> getProducts();

}
