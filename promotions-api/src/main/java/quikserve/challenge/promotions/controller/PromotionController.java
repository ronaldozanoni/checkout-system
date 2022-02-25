package quikserve.challenge.promotions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(value = "/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Void>> create(@RequestBody @Valid final PromotionRequest promotionRequest) {
        return promotionService.create(promotionRequest)
                .map(promo -> ResponseEntity
                        .created(URI.create("/promotions/" + promo.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .build());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Promotion>>> getAll(@Param("promotionType") String promotionType) {
        return promotionService.getAll()
                .map(promos -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(promos));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Promotion>> getById(@PathVariable final String id) {
        return promotionService.getById(id)
                .map(promo -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(promo));
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Promotion>> update(@PathVariable final String id,
                                                  @Valid @RequestBody final PromotionRequest promotionRequest) {
        return promotionService.update(id, promotionRequest)
                .map(body -> ResponseEntity.ok(body));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Promotion>> update(@Valid @RequestBody final PromotionRequest promotionRequest) {
        return promotionService.update(promotionRequest)
                .map(promo -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(promo));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@RequestBody final PromotionRequest promotionRequest) {
        promotionService.delete(promotionRequest).subscribe();
    }
}
