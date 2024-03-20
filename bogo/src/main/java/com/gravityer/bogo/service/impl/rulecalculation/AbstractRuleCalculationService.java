package com.gravityer.bogo.service.impl.rulecalculation;

import com.gravityer.bogo.dao.PurchaseHistoryDao;
import com.gravityer.bogo.dto.RuleCalculationResponseDto;
import com.gravityer.bogo.service.RuleCalculationService;
import com.gravityer.bogo.service.features.RuleCalculationServiceFeatureName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractRuleCalculationService implements RuleCalculationService {

    @Autowired
    protected PurchaseHistoryDao purchaseHistory;

    public void recordHistory(RuleCalculationServiceFeatureName ruleCode, RuleCalculationResponseDto calculation) {
        purchaseHistory.addPurchaseHistory(ruleCode, calculation);
    }

    @Override
    public List<RuleCalculationResponseDto> history(RuleCalculationServiceFeatureName ruleCode) {
        return purchaseHistory.getAllPurchasesHistory().get(ruleCode);
    }
}
