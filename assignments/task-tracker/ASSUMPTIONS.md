## ✅ Основной перечень знаний для старта изучения Spring Boot

---

### 1. **Базовый Java (Java SE)**
Без этого — невозможно понимать, как работает Spring.

#### Что нужно знать:
- Переменные, типы данных, примитивы
- Управляющие конструкции: `if`, `for`, `while`, `switch`
- Методы: `public static void main(String[] args)`
- Классы и объекты
- Наследование, инкапсуляция, полиморфизм
- `String`, `StringBuilder`, `Wrapper` (Integer, Boolean и т.д.)
- Коллекции: `List`, `Set`, `Map`, `ArrayList`, `HashMap`, `LinkedHashMap`
- Обработка исключений: `try-catch`, `throws`, `throw`, `Exception` vs `RuntimeException`
- Работа с `String`, `StringBuilder`, `StringBuffer`
- Модификаторы доступа: `private`, `public`, `protected`, `default`


> применял в проекте

---

### 2. **OOP (Объектно-ориентированное программирование)**
Spring — это паттерны, шаблоны, DI, AOP. Без OOP — непонятно, что происходит.

#### Что нужно знать:
- Классы, объекты, инкапсуляция
- Наследование, полиморфизм, полиморфизм в `equals()`/`hashCode()`
- Абстрактные классы, интерфейсы
- `abstract`, `interface`, `implements`
- `this`, `super`, `static`, `final`

> концептуально знаю, применяю в Python, в Java реализовал equals, hashcode, конструкторы

---

### 3. **Работа с зависимостями (Maven или Gradle)**
Spring Boot — это **фреймворк с зависимостями**. Без понимания — не заработает.

#### Что нужно знать:
- Что такое `pom.xml` (Maven) или `build.gradle` (Gradle)
- Как добавлять зависимости (например, `spring-boot-starter-web`)
- Что такое `groupId`, `artifactId`, `version`
- Разница между `compile`, `test`, `provided`, `optional`
- Как собирать `jar`-файл: `mvn package` / `./gradlew build`

> Maven - умею находить описания зависимостей, добавлять в проект (pom.xml), применияю mvn clean package, собирвю jar

---

### 4. **Работа с консолью и командной строкой (CLI)**
Без этого — не запустить `java -jar`, `mvn`, `./gradlew`.

#### Что нужно знать:
- Работа в терминале (macOS/Linux) / командной строке (Windows)
- Команды: `cd`, `ls`, `dir`, `mkdir`, `pwd`, `java -jar`, `mvn clean package`
- Работа с файлами и папками
- Путь к `jar`-файлу

> уверенно пользуюсь консолью в эмуляторе терминала и программно при вызове программ из кода и при направлении вывода

---

### 5. **HTTP и REST (базовый уровень)**
Spring Boot — это веб-фреймворк. Без понимания HTTP — будет непонятно, что делают `@RestController`, `@GetMapping`.

#### Что нужно знать:
- Что такое HTTP-методы: `GET`, `POST`, `PUT`, `DELETE`
- Что такое `status code`: `200`, `404`, `500`
- Что такое `path`, `query parameters`, `headers`, `body`
- Что такое `JSON` — как формат обмена данными
- Как читать `curl`-запросы

> знаю концепт, работаю с запросами в Python (requests, aiohttp), структуру json знаю, curl применяю с файлом конфигурации

---

### 6. **Java 8+ (обязательно!)**
Spring Boot 3+ требует **Java 17 или 21**. Ранние версии — Java 11.

#### Что нужно знать:
- Лямбды: `() -> {}`, `list.forEach(System.out::println)`
- Stream API: `filter`, `map`, `collect`, `stream()`
- Optional: `Optional.ofNullable()`, `orElse()`, `orElseThrow()`
- `Function`, `BiFunction`, `Consumer`, `Predicate`

> в Python применяю, в Java - при гуглении - применяю stream и lambda 

---

### 7. **Простые шаблоны проектирования (на уровне понимания)**
Не нужно знать все паттерны, но понимать, что такое:

- **Зависимость вставки (Dependency Injection)** — «внедрение зависимостей»
- **ООП-принципы**: SOLID (в общих чертах)
- **Контейнеры**: Spring — это контейнер, управляющий объектами

> слабо, не практиковал

---

### 8. **Работа с базой данных (базовый уровень)**
Даже если не будешь сразу писать SQL — нужно понимать:

- Что такое `CREATE TABLE`, `INSERT`, `SELECT`, `JOIN`
- Как подключиться к `H2`, `HSQLDB`, `PostgreSQL`, `MySQL`
- Что такое `JPA`, `JdbcTemplate`, `JPA Repository`

> знаю переменные, подзапросы, оконные функции, работал с MS SQL Server, MySQL 

---

### 9. **Работа с `@SpringBootApplication` и `main`-методом**
Понимать, что делает:

```java
@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

> впервые вижу

---

### 10. **Инструменты (на уровне понимания)**
- `Postman` или `curl` — тестировать API
- `IntelliJ IDEA` или `VS Code` — редактор
- `Git` — не обязательно, но полезно

> уверенно пользуюсь, на постмене автоматизировал с JS
---
