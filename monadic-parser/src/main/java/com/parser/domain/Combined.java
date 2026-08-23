package com.parser.core;

import java.util.List;

/**
 * Результат парсинга выражения
 * Пример:
 * Combined(1, [Suffix(add, 2), Suffix(sub, 3)])
 * для выражения `1 + 2 - 3`
 * @param head
 * @param tail
 */
public record Combined(Integer head, List<Suffix> tail) {
}
