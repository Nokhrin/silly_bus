package com.parser;

import java.io.IOException;
import java.util.logging.LogManager;

/**
 * Инициализация логирования для тестов.
 * Вызывать один раз перед запуском тестов.
 */
public class TestConfig {

    static {
        try {
            LogManager.getLogManager()
                    .readConfiguration(TestConfig.class.getResourceAsStream("/logging.properties"));
        } catch (IOException e) {
            System.err.println("Failed to load logging config: " + e.getMessage());
        }
    }

    /**
     * Включить отладочное логирование для конкретного класса.
     * Использовать в тестах для отладки.
     */
    public static void enableDebugLogging(Class<?> clazz) {
        java.util.logging.Logger.getLogger(clazz.getName())
                .setLevel(java.util.logging.Level.FINEST);
    }
}