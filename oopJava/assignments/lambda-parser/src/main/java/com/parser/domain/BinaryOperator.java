package com.parser.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Операторы мат операций
 */
public enum BinaryOperator {
    ADD('+'),
    SUB('-'),
    MUL('*'),
    DIV('/');

    private final char operator;

    BinaryOperator(char operator) {
        this.operator = operator;
    }

    public char getOperator() {
        return operator;
    }

    /**
     * Возвращает операцию по символу операции
     */
    private static final Map<Character, BinaryOperator> LOOKUP_MAP = new HashMap<>();
    static {
        for (BinaryOperator operation : values()) {
            LOOKUP_MAP.put(operation.operator, operation);
        }
    }

    public static BinaryOperator valueOf(char c) {
        return LOOKUP_MAP.get(c);
    }
}

