package qikserve.challenge.checkout.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import qikserve.challenge.checkout.model.Product;

import java.util.List;

@FeignClient("products")
public interface ProductsAPIClient {

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    List<Product> getProducts();

}
