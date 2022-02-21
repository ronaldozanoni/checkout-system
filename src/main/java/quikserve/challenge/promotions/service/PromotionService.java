package quikserve.challenge.promotions.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import quikserve.challenge.promotions.dto.PromotionRequest;
import quikserve.challenge.promotions.mapper.PromotionMapper;
import quikserve.challenge.promotions.model.Promotion;
import quikserve.challenge.promotions.model.Rule;
import quikserve.challenge.promotions.repository.PromotionsRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ValidationException;
import java.util.Objects;

@Slf4j
@Service
public class PromotionService {

    @Autowired
    private PromotionsRepository promotionsDB;

    @Autowired
    private PromotionMapper promotionMapper;

    public Mono<Promotion> create(final PromotionRequest promotionRequest) {
        return Mono.just(promotionRequest)
                .map(this::validateRules)
                .map(promotionMapper::toEntity)
                .flatMap(promotionsDB::save)
                .doOnSuccess(persisted -> log.info("msg=Success saving promotion id={}", persisted.getId()))
                .doOnError(err -> log.error("msg=Failed to save promotion", err));
    }

    public Flux<Promotion> getAll() {
        return promotionsDB.findAll()
                .doOnComplete(() -> log.info("msg=Success fetching promotions"))
                .doOnError(err -> log.error("msg=Failed to fetch promotions", err));
    }

    public Mono<Promotion> getById(String id) {
        return promotionsDB.findById(id)
                .doOnSuccess(promo -> log.info("msg=Success fetching promo with ID={} promo={}", id, promo))
                .doOnError(err -> log.error("msg=Failed to fetch promotion with ID={}", id, err));
    }

    public Mono<Promotion> update(String id, PromotionRequest promotionRequest) {
        return Mono.just(promotionRequest)
                .map(this::validateRules)
                .map(promotionMapper::toEntity)
                .map(promo -> {
                    promo.setId(id);
                    return promo;
                })
                .flatMap(promotionsDB::save)
                .doOnSuccess(persisted -> log.info("msg=Success updating promotion with ID={}", id))
                .doOnError(err -> log.error("msg=Failed to update promotion with ID={}", id, err));
    }

    public Mono<Void> delete(PromotionRequest promotionRequest) {
        return promotionsDB.delete(promotionMapper.toEntity(promotionRequest))
                .doOnSuccess(ok -> log.info("msg=Success deleting promotion with ID={}", promotionRequest.getId()))
                .doOnError(err -> log.error("msg=Failed to delete promotion with ID={}", promotionRequest.getId(), err));
    }

    private PromotionRequest validateRules(PromotionRequest promotion) {
        final Rule rule = promotion.getRules();
        if (Objects.isNull(rule.getMinValue()) && (Objects.isNull(rule.getProducts()) || rule.getProducts().isEmpty())) {
            throw new ValidationException("You must inform either a minimum value or a list of products for the promotion");
        }
        return promotion;
    }
}
