# Функциональный калькулятор

Построен на парсер-комбинаторах

## Установка и запуск
**Требования:** JDK 25+, Maven 3.8+

**Сборка:**
```bash
mvn clean package -DskipTests
```

**Запуск:**
```bash
java -jar target/parser-combinator-1.0.0.jar
```

**Примеры:**
```text
Expression: 2+3*4
Result: 14.0

Expression: (10-5)/0
Arithmetic Error: Делитель равен нулю
```