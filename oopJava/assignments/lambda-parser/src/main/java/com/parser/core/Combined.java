package com.parser.core;

import java.util.List;

/**
 * Результат парсинга выражения
 * Пример: `1 + 2 - 3`
 * head=1, tail=List[Suffix(add, 2), Suffix(sub, 3)]
 * @param head
 * @param tail
 */
public record Combined(Integer head, List<Suffix> tail) {
}
