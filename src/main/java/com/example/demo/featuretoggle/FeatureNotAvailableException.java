package com.example.demo.featuretoggle;

public class FeatureNotAvailableException extends RuntimeException {
    public FeatureNotAvailableException(String message) {
        super(message);
    }
}
