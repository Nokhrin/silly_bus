# Функциональный парсер-комбинатор

Функциональный парсер-комбинатор на Java 21 для разбора подмножества eBNF.
Контракт: Parser<A> = (String, int) → Optional<Parsed<A>>, без null.
Базовые парсеры (char, digit, string) комбинируются через plus, or, repeat для построения правил грамматики.
Рекурсивные ссылки поддерживаются через ProxyParser с отложенной инициализацией.
Целевое применение: валидация DSL, предобработка ввода для аналитических пайплайнов, прототипирование бизнес-правил.


## Тесты

```shell
# Выполнить все тесты проекта
mvn test

# Выполнить тесты группы "flatMap"
mvn test -Dgroups=flatMap

# Выполнить тесты нескольких групп (логическое ИЛИ)
mvn test -Dgroups=flatMap,or

# Исключить группу
mvn test -DexcludedGroups=map

# Выполнить тест-комплект по конфигу
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng-sprint-2.xml
```