package quikserve.challenge.promotions.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import quikserve.challenge.promotions.model.Promotion;

@Repository
public interface PromotionsRepository extends ReactiveMongoRepository<Promotion, String> {
}
