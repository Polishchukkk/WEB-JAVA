package com.example.demo.featuretoggle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FeatureToggleService {

    @Value("${feature.cosmoCats.enabled:true}")
    private boolean isCosmoCatsEnabled;

    @Value("${feature.kittyProducts.enabled:false}")
    private boolean isKittyProductsEnabled;

    public boolean isFeatureEnabled(String featureName) {
        return switch (featureName) {
            case "cosmoCats" -> isCosmoCatsEnabled;
            case "kittyProducts" -> isKittyProductsEnabled;
            default -> false;
        };
    }
}