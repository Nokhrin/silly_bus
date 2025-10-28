# идея для развития контрольного проекта

Отличный вопрос! Для опытного программиста, который **начинает свой путь в Java**, идеальный проект на Spring Boot — это **не просто "Hello World"**, а **настоящий полноценный стартовый проект с архитектурой, безопасностью, тестированием, CI/CD и реальными паттернами**.

---

## 🎯 Цель проекта:
> **Создать мини-версию "системы управления задачами" (Task Management System) на Spring Boot, но с продвинутой архитектурой — как если бы это был реальный фронтенд-бэкенд стартап.**

---

## 🚀 Название проекта (пример):
> **TaskFlow** — лёгкая, но мощная система управления задачами с правами, историями, уведомлениями, аудитом и REST API.

---

## ✅ Основные цели проекта:
| Цель | Пояснение |
|------|----------|
| Научиться архитектуре Spring Boot | MVC, JPA, Security, Validation |
| Понять, как писать **чистый код** | Layered Architecture, DDD, Hexagonal Architecture |
| Научиться писать **надёжные тесты** | Unit + Integration + MockMvc |
| Настроить **CI/CD** (GitHub Actions) | Автотесты, сборка, деплой |
| Использовать **реальные паттерны** | Builder, Strategy, Factory, Observer |
| Добавить **безопасность** | Spring Security + JWT |
| Сделать **микросервисную архитектуру** (на будущее) | Разделить на `task-service`, `user-service` |

---

## 🛠️ Технологии (Spring Boot + современные практики)

| Категория | Технологии |
|---------|-----------|
| Ядро | Spring Boot 3.x (Java 17), Lombok, MapStruct |
| БД | H2 (для тестов), PostgreSQL (для продакшена) |
| ORM | Spring Data JPA, Hibernate |
| Безопасность | Spring Security + JWT |
| API | REST, `@RestController`, `@Valid`, `@NotNull` |
| Тесты | JUnit 5, Mockito, @MockBean, @WebMvcTest, @DataJpaTest |
| Логирование | Lombok + `@Slf4j` |
| Конфигурация | `@ConfigurationProperties`, `@Configuration`, `@Profile` |
| CI/CD | GitHub Actions (собирать, тестировать, деплоить) |
| Мониторинг | Actuator (health, info, metrics) |
| Swagger / OpenAPI | `springdoc-openapi` — красивая документация API |

---

## 📁 Структура проекта (пример)

```bash
taskflow/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.taskflow/
│   │   │   │   ├── TaskFlowApplication.java
│   │   │   │   ├── config/
│   │   │   │   │   ├── SecurityConfig.java
│   │   │   │   │   ├── JpaConfig.java
│   │   │   │   │   └── OpenApiConfig.java
│   │   │   │   ├── controller/
│   │   │   │   │   └── TaskController.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── TaskService.java
│   │   │   │   │   └── impl/TaskServiceImpl.java
│   │   │   │   ├── repository/
│   │   │   │   │   └── TaskRepository.java
│   │   │   │   ├── model/
│   │   │   │   │   ├── Task.java
│   │   │   │   │   └── User.java
│   │   │   │   ├── dto/
│   │   │   │   │   ├── TaskDto.java
│   │   │   │   │   └── UserDto.java
│   │   │   │   └── exception/
│   │   │   │   │   ├── TaskNotFoundException.java
│   │   │   │   │   └── BusinessException.java
│   │   │   └── aspect/
│   │   │       └── LoggingAspect.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── data.sql
│   │       ├── schema.sql
│   │       └── application-dev.yml
│   └── test/
│       └── java/
│           └── com.taskflow/
│               ├── TaskFlowApplicationTests.java
│               ├── integration/
│               │   └── TaskControllerIT.java
│               └── unit/
│                   ─── TaskServiceTest.java
├── pom.xml
├── .github/workflows/ci.yml
└── README.md
```

---

## 🎯 Основные фичи (что будет реализовано)

| Фича | Описание |
|------|--------|
| ✅ Создание задачи | `POST /api/tasks` |
| ✅ Получение списка задач | `GET /api/tasks` |
| ✅ Обновление задачи | `PUT /api/tasks/{id}` |
| ✅ Удаление задачи | `DELETE /api/tasks/{id}` |
| ✅ Назначение задачи пользователю | `PATCH /api/tasks/{id}/assign` |
| ✅ Отметка "выполнено" | `PATCH /api/tasks/{id}/done` |
| ✅ Аудит изменений | Логирование `modify`, `owner`, `done` |
| ✅ Права доступа | Только `admin` может удалять, `user` — только свои задачи |
| ✅ JWT-аутентификация | `POST /auth/login` → токен |
| ✅ Swagger UI | `http://localhost:8080/swagger-ui.html` |
| ✅ Тесты на 90% покрытия | JUnit + Mockito |
| ✅ CI/CD | GitHub Actions: тесты, сборка, деплой на `localhost` или `fly.io` |

---

## 🧠 Что ты получишь, пройдя этот путь?

| Навык | Что получишь |
|------|-------------|
| 🧱 Архитектура | Понимание, как делить на `controller`, `service`, `repository`, `dto`, `entity` |
| 🔐 Безопасность | JWT, Spring Security, `@PreAuthorize`, `@RolesAllowed` |
| 📊 Тесты | Unit, Integration, MockMvc, `@WebMvcTest`, `@DataJpaTest` |
| ⚙️ CI/CD | GitHub Actions: автоматическая сборка, тесты, деплой |
| 📦 Паттерны | Builder, Strategy, Factory, AOP (логирование) |
| 📈 Опыт реального проекта | Готово к резюме, GitHub, собеседованию |

---

## 🎁 Дополнительно (если хочешь углубиться):

| Фича | Как реализовать |
|------|----------------|
| Уведомления | `@Async` + `ApplicationEvent` (например, "задача назначена") |
| Аудит изменений | `@EntityListeners(AuditingEntityListener.class)` |
| Реестр действий | `EventPublisher` + `@Transactional` |
| Кэширование | `@Cacheable`, `@CacheEvict` |
| Мониторинг | Actuator + Prometheus + Grafana (на позже) |
| Docker | `Dockerfile`, `docker-compose.yml` |

---

## ✅ Готово к деплою?

- Запусти локально: `./mvnw spring-boot:run`
- Открой: `http://localhost:8080/swagger-ui.html`
- Проверь: `POST /auth/login` → получи токен
- Используй токен в `Authorization: Bearer <token>`

---

## 📌 Резюме

> **TaskFlow** — это **идеальный стартовый проект** для опытного программиста, который хочет:
> - Настречься на Java
> - Понять, как устроен современный Spring Boot
> - Научиться писать **надёжный, тестируемый, безопасный код**
> - Сделать **готовый к резюме** проект с CI/CD

---



---


# предложения о доработке учебной программы

## Общие требования / соглашения о написании кода
- сохранять исходные объекты / поддерживать иммутабельность
  - хорошая привычка разработки
- строго определять типы, воздерживаться `var`
  - понять типизацию

### порядок реализации методов, применяющих циклы
- javadoc
  - определить задачу, которую решает метод
  - определить критерии значений параметров метода
- кооменты в методе
  - определить инвариант
  - определить базу индукции / рекурсии
  - определить шаг индукции / рекурсии
- разработать юнит-тесты
- реализовать алгоритм

### для алгоритмических задач
- не использовать встроенные реализации алгоритмов
- не использовать сторонние библиотеки / надстройки
  - например, `guava`


## дополнить темы уроков

### [урок 0 - основные понятия, настройка проекта](src/main/java/pop/lesson00/README.md)

- настройка .gitignore
  - служебные каталоги и файлы Maven, IDEA
- проект, атрибуты проекта
- модуль
- либа/пакет/библиотека проекта
- либа/пакет/библиотека глобальная
- артефакт
- SDK

- [Project/Проект в среде разработки IDEA](https://www.jetbrains.com/help/idea/2022.2/working-with-projects.html)


- работа оффлайн в `IntelliJ IDEA`
  - [документация SDK, внешних библиотек](https://www.jetbrains.com/help/idea/working-offline.html#sdk-open-offline)
  - [зависимости Maven](https://www.jetbrains.com/help/idea/working-offline.html#maven)

- jshell - 
  - математические операции
  - операции со строками



### [урок 1](src/main/java/pop/lesson01/README.md)

#### добавить топики

- примитивы и специальные типы
- String и операции первой необходимости
- практика на базовые операции
  - объединение строк
  - понимание неизменяемости строк
  - эффективное использование памяти при создании строки
    - с практической точки зрения, без деталей реализации
    - пример - `pop.lesson01.StringBasics.convertToBinary`


#### уточнить постановку задачи

- Создайте `Main.java`, выведите `Привет, [Имя]! Сегодня [число] урок Java.`
  - как передаются значения?
- Добавьте вывод длины имени и сумму `7 + 3`
  - уточнить формат вывода

### [урок 2](src/main/java/pop/lesson02/README.md)

#### добавить топики 
- примитивы
  - простая математика с примитивами
  - граничные случаи типов данных (например, попытка передать `long` в `int`)


### [урок 3](src/main/java/pop/lesson03/README.md)
- добавил задачу на создание строки
- дополнить тему
  - \+ коллекции первой необходимости
  - практика на базовые операции
- Рассмотреть
  - объекты
    - Array
    - Collection
      - ArrayList
      - HashSet
      - Map
      - SortedMap
- Базовые операции для рассмотренных объектов
  - практика

### [урок 6](src/main/java/pop/lesson06/README.md)
- результат выполнения урока - bash скрипт, создающий jar
- настройка точки входа программы в манифесте
- пояснить назначение скомпилированных файлов с "долларом"
  - target/classes/pop/lesson04
    ├── ScannerInput$1.class
    ├── ScannerInput$2.class
    ├── ScannerInput$3.class
    ├── ScannerInput$4.class
    └── ScannerInput.class

### [урок 7](src/main/java/pop/lesson07/README.md)

#### добавить ссылки
- [Maven in 5 Minutes (Apache)](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
- [Краткое знакомство с Maven](https://tproger.ru/articles/maven-short-intro)
- [Maven — зачем?](https://habr.com/ru/articles/78252/)
- [Стандартная структура каталога проекта Maven](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html)
- [Настройка Maven](https://github.com/gochaorg/blog/blob/master/itdocs/maven/docs/official-configuring-maven.md)
- [настройка/применение Maven в среде разработки IDEA](https://www.jetbrains.com/help/idea/maven-support.html)

### [урок 8](src/main/java/pop/lesson08/README.md)
- добавить в задачу использование логирования
- показать принятый порядок работы с `Logger` в Java

### [урок 9](src/main/java/pop/lesson09/README.md)
- добавить задачу
  - научиться формулировать требования к проектируемой системе
  - выработать последовательность действий при проектировании и разработке
- определить структуру требований
- пример

---
