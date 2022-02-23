package quikserve.challenge.promotions.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import quikserve.challenge.promotions.dto.PromotionRequest;
import quikserve.challenge.promotions.exception.NotFoundException;
import quikserve.challenge.promotions.mapper.PromotionMapper;
import quikserve.challenge.promotions.model.Promotion;
import quikserve.challenge.promotions.model.Rule;
import quikserve.challenge.promotions.repository.PromotionsRepository;
import reactor.core.publisher.Mono;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PromotionService {

    @Autowired
    private PromotionsRepository promotionsDB;

    @Autowired
    private PromotionMapper promotionMapper;

    public Mono<Promotion> create(final PromotionRequest promotionRequest) {
        return Mono.just(promotionRequest)
                .map(promotionMapper::toEntity)
                .flatMap(promotionsDB::save)
                .doOnSuccess(persisted -> log.info("msg=Success saving promotion id={}", persisted.getId()))
                .doOnError(err -> log.error("msg=Failed to save promotion", err));
    }

    public Mono<List<Promotion>> getAll() {
        return promotionsDB.findAll()
                .collect(Collectors.toList())
                .doOnSuccess(promos -> log.info("msg=Success fetching promotions"))
                .doOnError(err -> log.error("msg=Failed to fetch promotions", err));
    }

    public Mono<Promotion> getById(String id) {
        return promotionsDB.findById(id)
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.error(new NotFoundException("Promotion not found")))
                .doOnSuccess(promo -> log.info("msg=Success fetching promo with ID={}", id))
                .doOnError(err -> log.error("msg=Failed to fetch promotion with ID={}", id, err));
    }

    public Mono<Promotion> update(String id, PromotionRequest promotionRequest) {
        return Mono.just(promotionRequest)
                .map(promotionMapper::toEntity)
                .zipWith(this.getById(id))
                .map(promos -> promotionMapper.merge(promos.getT1(), promos.getT2()))
                .flatMap(promotionsDB::save)
                .doOnSuccess(persisted -> log.info("msg=Success updating promotion with ID={}", id))
                .doOnError(err -> log.error("msg=Failed to update promotion with ID={}", id, err));
    }

    public Mono<Promotion> update(PromotionRequest promotionRequest) {
        return Mono.just(promotionRequest)
                .filter(promo -> Objects.nonNull(promo.getId()))
                .switchIfEmpty(Mono.error(new ValidationException("ID can't be null!")))
                .map(promotionMapper::toEntity)
                .flatMap(promotionsDB::save)
                .doOnSuccess(promo -> log.info("msg=Success updating promotion with ID={}", promo.getId()))
                .doOnError(err -> log.error("msg=FAiled to update promotion with ID={}", promotionRequest.getId(), err));
    }

    public Mono<Void> delete(PromotionRequest promotionRequest) {
        return promotionsDB.deleteById(promotionRequest.getId())
                .doOnSuccess(ok -> log.info("msg=Success deleting promotion with ID={}", promotionRequest.getId()))
                .doOnError(err -> log.error("msg=Failed to delete promotion with ID={}", promotionRequest.getId(), err));
    }
}
