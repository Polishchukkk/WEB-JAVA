package com.example.demo.service;

import com.example.demo.featuretoggle.FeatureToggleService;
import com.example.demo.featuretoggle.FeatureNotAvailableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class CosmoCatServiceTests {

    @Mock
    private FeatureToggleService featureToggleService;

    @InjectMocks
    private CosmoCatService cosmoCatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCosmoCatsFeatureEnabled() {
        // Імітуємо, що флаг увімкнений
        Mockito.when(featureToggleService.isFeatureEnabled("cosmoCats")).thenReturn(true);

        // Викликаємо метод і перевіряємо результат
        String result = cosmoCatService.getCosmoCats();
        Assertions.assertEquals("Fetching Cosmo Cats data...", result);
    }

    @Test
    void testCosmoCatsFeatureDisabled() {
        // Імітуємо, що флаг вимкнений
        Mockito.when(featureToggleService.isFeatureEnabled("cosmoCats")).thenReturn(false);

        // Перевіряємо, що метод кидає виключення
        Assertions.assertThrows(FeatureNotAvailableException.class, () -> {
            cosmoCatService.getCosmoCats();
        });
    }
}
