package com.gravityer.bogo.service;

import com.gravityer.bogo.dto.RuleCalculationResponseDto;
import com.gravityer.bogo.service.features.RuleCalculationServiceFeatureName;

import java.util.List;

public interface RuleCalculationService extends GenericAppService<RuleCalculationServiceFeatureName> {

    default RuleCalculationResponseDto runRule(RuleCalculationServiceFeatureName ruleCode, List<Integer> costOfAllProducts) {
        throw notImplementedException();
    }

    default List<RuleCalculationResponseDto> history(RuleCalculationServiceFeatureName ruleCode) {
        throw notImplementedException();
    };

}
