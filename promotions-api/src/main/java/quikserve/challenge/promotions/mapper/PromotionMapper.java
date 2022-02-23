package quikserve.challenge.promotions.mapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;
import quikserve.challenge.promotions.dto.PromotionRequest;
import quikserve.challenge.promotions.model.Promotion;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

@Component
public class PromotionMapper {

    public Promotion toEntity(PromotionRequest promotionRequest) {
        final Promotion entity = new Promotion();
        BeanUtils.copyProperties(promotionRequest, entity);
        return entity;
    }

    public Promotion merge(Promotion source, Promotion target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
        return target;
    }

    // Now this really shouldn't be here I know
    private String[] getNullPropertyNames(Promotion source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}
