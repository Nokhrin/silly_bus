#!/usr/bin/env python3
"""Построение таблицы истинности для заданного логического выражения."""
import sys
import re
from itertools import product


def find_variables(expr: str) -> list[str]:
    """Извлекает имена переменных из выражения (однобуквенные идентификаторы)."""
    return sorted(set(re.findall(r'\b[a-z]\b', expr)))


def evaluate(expr: str, env: dict) -> bool:
    """Вычисляет значение выражения в заданном окружении."""
    return bool(eval(expr, {"__builtins__": {}}, env))


def format_value(v: bool) -> str:
    return "T" if v else "F"


def truth_table(expr: str) -> None:
    variables = find_variables(expr)
    if not variables:
        print("Переменные не найдены")
        return

    header = " | ".join(variables) + " | " + expr
    print(header)
    print("-" * len(header))

    for values in product([False, True], repeat=len(variables)):
        env = dict(zip(variables, values))
        result = evaluate(expr, env)
        row = " | ".join(format_value(v) for v in values) + " | " + format_value(result)
        print(row)


if __name__ == "__main__":
    if len(sys.argv) != 2:
        print(f"Использование: {sys.argv[0]} '<выражение>'")
        print("Пример: python3 tt.py 'a and (b or not c)'")
        sys.exit(1)

    truth_table(sys.argv[1])