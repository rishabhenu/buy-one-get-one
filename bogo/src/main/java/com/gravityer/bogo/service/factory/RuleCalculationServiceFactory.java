package com.gravityer.bogo.service.factory;

import com.gravityer.bogo.service.RuleCalculationService;
import com.gravityer.bogo.service.features.RuleCalculationServiceFeatureName;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RuleCalculationServiceFactory extends AbstractFeatureServiceFactory<RuleCalculationServiceFeatureName, RuleCalculationService> {

    public RuleCalculationServiceFactory(List<RuleCalculationService> featuredServices) {
        super(featuredServices);
    }
}
