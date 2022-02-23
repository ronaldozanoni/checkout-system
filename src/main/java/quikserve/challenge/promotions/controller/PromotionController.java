package quikserve.challenge.promotions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quikserve.challenge.promotions.dto.PromotionRequest;
import quikserve.challenge.promotions.model.Promotion;
import quikserve.challenge.promotions.service.PromotionService;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping
    public Mono<ResponseEntity<Void>> create(@RequestBody @Valid final PromotionRequest promotionRequest) {
        return promotionService.create(promotionRequest)
                .map(promo -> ResponseEntity.created(URI.create("/promotions/" + promo.getId())).build());
    }

    @GetMapping
    public Mono<ResponseEntity<List<Promotion>>> getAll() {
        return promotionService.getAll()
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Promotion>> getById(@PathVariable final String id) {
        return promotionService.getById(id)
                .map(ResponseEntity::ok);
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<Promotion>> update(@PathVariable final String id,
                                                  @Valid @RequestBody final PromotionRequest promotionRequest) {
        return promotionService.update(id, promotionRequest)
                .map(ResponseEntity::ok);
    }

    @PutMapping
    public Mono<ResponseEntity<Promotion>> update(@Valid @RequestBody final PromotionRequest promotionRequest) {
        return promotionService.update(promotionRequest)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> delete(@RequestBody final PromotionRequest promotionRequest) {
        return promotionService.delete(promotionRequest)
                .map(ok -> ResponseEntity.status(204).build());
    }
}
