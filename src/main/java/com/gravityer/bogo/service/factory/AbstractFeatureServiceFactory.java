package com.gravityer.bogo.service.factory;

import com.gravityer.bogo.config.exceptions.ProgramException;
import com.gravityer.bogo.service.GenericAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class AbstractFeatureServiceFactory<F extends Enum<F>, T extends GenericAppService<F>> {

    private final Map<F, T> servicesMap = new ConcurrentHashMap<>();
    private T defaultService;

    public AbstractFeatureServiceFactory(List<T> featuredServices) {
        if (CollectionUtils.isEmpty(featuredServices)) {
            log.info("No service found under factory: {}", getFactoryName());
        }
        featuredServices.forEach(service -> {
            if (CollectionUtils.isEmpty(service.features())) {
                if (Objects.isNull(defaultService)) {
                    defaultService = service;
                    return;
                } else {
                    throw new ProgramException("Duplicate default service found for factory: " + getFactoryName() +
                        " Previous default service: " + defaultService.getClass() + ". New service: " + service.getClass());
                }
            }
            service.features().forEach(feature -> {
                if (servicesMap.containsKey(feature)) {
                    throw new ProgramException("Duplicate service found for feature " + feature + " under factory: " + getFactoryName());
                }
                servicesMap.put(feature, service);
            });
        });
        log.info("Service factory: {} initialized with total services size: {}", getFactoryName(), servicesMap.size());
    }

    public T getService(F feature) {
        return Optional.ofNullable(servicesMap.get(feature)).orElseThrow(() ->
            new ProgramException("No service for feature " + feature + " found under factory: " + getFactoryName()));
    }

    public String getFactoryName() {
        return getClass().getName();
    }

}
