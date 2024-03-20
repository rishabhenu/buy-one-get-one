package com.gravityer.bogo.dto;

import com.gravityer.bogo.service.features.RuleCalculationServiceFeatureName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Getter
@Setter
public class RuleCalculationResponseDto {

    private RuleCalculationServiceFeatureName ruleCode;
    private final List<Integer> boughtProductCosts = new ArrayList<>();
    private final List<Integer> discountedProductCosts = new ArrayList<>();

    public RuleCalculationResponseDto addBoughtProductCost(Integer cost) {
        boughtProductCosts.add(cost);
        return this;
    }

    public RuleCalculationResponseDto addDiscountedProductCost(Integer cost) {
        discountedProductCosts.add(cost);
        return this;
    }

}
