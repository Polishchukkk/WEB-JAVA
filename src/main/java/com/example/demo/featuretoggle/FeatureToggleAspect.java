package com.example.demo.featuretoggle;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    @Autowired
    public FeatureToggleAspect(FeatureToggleService featureToggleService) {
        this.featureToggleService = featureToggleService;
    }

    @Before("execution(* com.example.demo.service.CosmoCatService.getCosmoCats(..))")
    public void checkCosmoCatsFeature() {
        if (!featureToggleService.isFeatureEnabled("cosmoCats")) {
            throw new FeatureNotAvailableException("The Cosmo Cats feature is not enabled.");
        }
    }
}
