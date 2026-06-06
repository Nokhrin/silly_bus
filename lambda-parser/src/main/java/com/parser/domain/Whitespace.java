package com.parser.core;

/**
 * Пробельный символ
 */
public record Whitespace() {
    public static final Whitespace INSTANCE = new Whitespace();
    public static Whitespace of() {
        return INSTANCE;
    }
}
