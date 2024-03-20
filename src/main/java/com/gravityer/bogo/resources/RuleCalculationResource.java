package com.gravityer.bogo.resources;

import com.gravityer.bogo.config.exceptions.ProgramException;
import com.gravityer.bogo.dto.GenericResponseDto;
import com.gravityer.bogo.dto.RuleCalculationResponseDto;
import com.gravityer.bogo.service.factory.RuleCalculationServiceFactory;
import com.gravityer.bogo.service.features.RuleCalculationServiceFeatureName;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/purchase")
public class RuleCalculationResource {

    private final RuleCalculationServiceFactory serviceFactory;

    @PostMapping("/discount/{rule}")
    public GenericResponseDto<RuleCalculationResponseDto> calculateRule(@PathVariable("rule") RuleCalculationServiceFeatureName featureName, @RequestBody List<Integer> productCost) {
        if (CollectionUtils.isEmpty(productCost)) {
            throw new ProgramException("At least 1 purchase has to be made");
        }
        return GenericResponseDto.ok(serviceFactory.getService(featureName).runRule(featureName, productCost));
    }

    @GetMapping("/history")
    public GenericResponseDto<MultiValueMap<RuleCalculationServiceFeatureName, RuleCalculationResponseDto>> history() {
        MultiValueMap<RuleCalculationServiceFeatureName, RuleCalculationResponseDto> result = new LinkedMultiValueMap<>();
        for (RuleCalculationServiceFeatureName value : RuleCalculationServiceFeatureName.values()) {
            List<RuleCalculationResponseDto> history = serviceFactory.getService(value).history(value);
            if (CollectionUtils.isNotEmpty(history)) {
                result.put(value, history);
            }
        }
        return GenericResponseDto.ok(result);
    }

}
