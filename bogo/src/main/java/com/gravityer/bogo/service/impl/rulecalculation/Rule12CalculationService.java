package com.gravityer.bogo.service.impl.rulecalculation;

import com.gravityer.bogo.dto.RuleCalculationResponseDto;
import com.gravityer.bogo.service.features.RuleCalculationServiceFeatureName;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;

@Service
public class Rule12CalculationService extends AbstractRuleCalculationService {

    @Override
    public List<RuleCalculationServiceFeatureName> features() {
        return Arrays.asList(RuleCalculationServiceFeatureName.RULE1, RuleCalculationServiceFeatureName.RULE2);
    }

    /**
     * Rule1
     * Customers can buy one product and get another product for free as long as the
     * price of the product is equal to or less than the price of the first product.
     * <p>
     * Rule2
     * Customers can buy one product and get another product for free as long as the
     * price of the product is less than the price of the corresponding product in pairs.
     *
     * @param ruleName      = rule to apply
     * @param inputProducts = all product costs that customer want to purchase
     * @return = return the maximized discount of customer
     */
    @Override
    public RuleCalculationResponseDto runRule(RuleCalculationServiceFeatureName ruleName, List<Integer> inputProducts) {
        List<Integer> costOfAllProducts = new ArrayList<>(inputProducts);
        RuleCalculationResponseDto result = new RuleCalculationResponseDto();
        result.setRuleCode(ruleName);
        costOfAllProducts.sort(Collections.reverseOrder());
        while (!costOfAllProducts.isEmpty()) {
            Integer boughtProduct = costOfAllProducts.remove(0);
            result.addBoughtProductCost(boughtProduct);
            Optional.ofNullable(getImmediateNextProduct(ruleName, costOfAllProducts, boughtProduct))
                .ifPresent(result::addDiscountedProductCost);
        }
        recordHistory(ruleName, result);
        return result;
    }

    public Integer getImmediateNextProduct(RuleCalculationServiceFeatureName ruleName, List<Integer> allProductCosts, Integer currentCost) {
        Iterator<Integer> iterator = allProductCosts.iterator();
        while (iterator.hasNext()) {
            Integer nextProductToCheckDiscount = iterator.next();
            if (predicateMap().get(ruleName).test(nextProductToCheckDiscount, currentCost)) {
                iterator.remove();
                return nextProductToCheckDiscount;
            }
        }
        return null;
    }

    public static Map<RuleCalculationServiceFeatureName, BiPredicate<Integer, Integer>> predicateMap() {
        Map<RuleCalculationServiceFeatureName, BiPredicate<Integer, Integer>> map = new EnumMap<>(RuleCalculationServiceFeatureName.class);
        map.put(RuleCalculationServiceFeatureName.RULE1, (a, b) -> a <= b);
        map.put(RuleCalculationServiceFeatureName.RULE2, (a, b) -> a < b);
        return map;
    }

}
