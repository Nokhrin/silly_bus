package com.parser.core;

/**
 * Хвостовая часть выражения
 * Пример: `+ 2` в выражении `1 + 2`
 */
public record Suffix(BinaryOperator operator, Integer value) {
}
