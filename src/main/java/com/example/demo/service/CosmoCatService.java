package com.example.demo.service;

import com.example.demo.featuretoggle.FeatureToggleService;
import com.example.demo.featuretoggle.FeatureNotAvailableException;
import org.springframework.stereotype.Service;

@Service
public class CosmoCatService {

    private final FeatureToggleService featureToggleService;

    public CosmoCatService(FeatureToggleService featureToggleService) {
        this.featureToggleService = featureToggleService;
    }

    public String getCosmoCats() {
        if (!featureToggleService.isFeatureEnabled("cosmoCats")) {
            throw new FeatureNotAvailableException("The Cosmo Cats feature is not enabled.");
        }
        return "Fetching Cosmo Cats data...";
    }
}
