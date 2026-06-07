# Функциональный калькулятор

Построен на парсер-комбинаторах

## Установка и запуск

**Требования:** JDK 25+, Maven 3.8+

**Сборка:**

```shell
mvn clean package -DskipTests
```

**Запуск:**

```shell
java -jar target/parser-combinator-1.0.0.jar
```

**Примеры:**

```text
Expression: 2+3*4
Result: 14.0

Expression: (10-5)/0
Arithmetic Error: Делитель равен нулю
```

## Разработка

### Тестирование

```shell
# Базовый запуск всех тестов
mvn test

# Групповой запуск (логическое ИЛИ)
mvn test -Dgroups=flatMap,or,plus

# Исключение группы
mvn test -DexcludedGroups=debug
```

### Отчеты и метрики

```shell
# Генерация Allure-отчета
mvn test allure:serve
# Server started at <http://127.0.0.1:38661>. Press <Ctrl+C> to exit
# Открыть отчет в браузере

# Статический анализ кода (Checkstyle)
mvn checkstyle:check

# Отчет по покрытию (JaCoCo)
mvn test jacoco:report
# Открытие: target/site/jacoco/index.html
```

