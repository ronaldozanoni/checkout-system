package quikserve.challenge.promotions.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import quikserve.challenge.promotions.dto.PromotionRequest;
import quikserve.challenge.promotions.model.Promotion;

@Component
public class PromotionMapper {

    public Promotion toEntity(PromotionRequest promotionRequest) {
        final Promotion entity = new Promotion();
        BeanUtils.copyProperties(promotionRequest, entity);
        return entity;
    }
}
