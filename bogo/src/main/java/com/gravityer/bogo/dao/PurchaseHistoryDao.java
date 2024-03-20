package com.gravityer.bogo.dao;

import com.gravityer.bogo.dto.RuleCalculationResponseDto;
import com.gravityer.bogo.service.features.RuleCalculationServiceFeatureName;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class PurchaseHistoryDao {

    private final MultiValueMap<RuleCalculationServiceFeatureName, RuleCalculationResponseDto> ruleWisePurchases = new LinkedMultiValueMap<>();

    public MultiValueMap<RuleCalculationServiceFeatureName, RuleCalculationResponseDto> getAllPurchasesHistory() {
        return new LinkedMultiValueMap<>(ruleWisePurchases);
    }

    public void addPurchaseHistory(RuleCalculationServiceFeatureName ruleCode, RuleCalculationResponseDto calculation) {
        ruleWisePurchases.add(ruleCode, calculation);
    }

}
