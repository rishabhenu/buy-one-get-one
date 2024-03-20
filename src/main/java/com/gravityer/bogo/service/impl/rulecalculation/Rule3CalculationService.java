package com.gravityer.bogo.service.impl.rulecalculation;

import com.gravityer.bogo.dto.RuleCalculationResponseDto;
import com.gravityer.bogo.service.features.RuleCalculationServiceFeatureName;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
public class Rule3CalculationService extends AbstractRuleCalculationService {

    @Override
    public List<RuleCalculationServiceFeatureName> features() {
        return Collections.singletonList(RuleCalculationServiceFeatureName.RULE3);
    }

    /**
     * Rule3
     * Customers can buy two products and get two products for free as long as the
     * price of the product is less than the price of the first product.
     *
     * @param ruleCode      = rule to apply
     * @param inputProducts = all product costs that customer want to purchase
     * @return = return the maximized discount of customer
     */
    @Override
    public RuleCalculationResponseDto runRule(RuleCalculationServiceFeatureName ruleCode, List<Integer> inputProducts) {
        List<Integer> costOfAllProducts = new ArrayList<>(inputProducts);
        RuleCalculationResponseDto result = new RuleCalculationResponseDto();
        result.setRuleCode(ruleCode);
        costOfAllProducts.sort(Collections.reverseOrder());
        while (costOfAllProducts.size() >= 2) {
            Integer boughtProduct = costOfAllProducts.remove(0);
            Integer secondProductBought = costOfAllProducts.remove(0);
            if (secondProductBought < boughtProduct) {
                result.addBoughtProductCost(boughtProduct);
                result.addDiscountedProductCost(secondProductBought);
            } else {
                result.addBoughtProductCost(boughtProduct).addBoughtProductCost(secondProductBought);
                result.getDiscountedProductCosts().addAll(getDiscountedProducts(costOfAllProducts, boughtProduct));
            }
        }
        recordHistory(ruleCode, result);
        return result;
    }

    private List<Integer> getDiscountedProducts(List<Integer> costOfAllProducts, Integer boughtProduct) {
        List<Integer> discountedProducts = new ArrayList<>(2);
        Iterator<Integer> iterator = costOfAllProducts.iterator();
        while (iterator.hasNext()) {
            if (discountedProducts.size() == 2) {
                break;
            }
            Integer productCostToTestDiscounted = iterator.next();
            if (productCostToTestDiscounted < boughtProduct) {
                iterator.remove();
                discountedProducts.add(productCostToTestDiscounted);
            }
        }
        return discountedProducts;
    }
}
