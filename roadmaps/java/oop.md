# 🚀 План обучения ООП в Java (с фокусом на современные фичи)

> **Цель**: Научиться проектировать типобезопасные системы с использованием современных фич Java (17+), включая `sealed`, `records`, pattern matching и generics.  
> **Особенность**: Все задания — часть единого проекта *«Система обработки финансовых операций»*. Каждый этап опирается на результаты предыдущего.

---

## 📌 Требования к окружению
- Java 17+ ( sealed-классы и records требуют Java 17)
- IDE: IntelliJ IDEA (рекомендуется) или VS Code с расширением для Java

---

## 🧩 Единый проект: Система обработки финансовых операций

### Неделя 1: Основы ООП (строительство каркаса)

#### Тема 1: Классы и объекты → Создаем базовую сущность
**Что изучаем**: классы, конструкторы, инкапсуляция, валидация  
**Практика**:
```java
public class Transaction {
    private String id;
    private double amount;
    private String currency;
    
    public Transaction(String id, double amount, String currency) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (!List.of("USD", "EUR", "RUB").contains(currency)) 
            throw new IllegalArgumentException("Unsupported currency");
        
        this.id = id;
        this.amount = amount;
        this.currency = currency;
    }
    
    // Геттеры + toString()
}
```
**Задание**:  
1. Создайте 3 объекта `Transaction` (2 валидных, 1 с ошибкой)  
2. Напишите метод `validate(Transaction t)`, возвращающий `true`/`false`

---

#### Тема 2: Наследование → Добавляем типы операций
**Что изучаем**: наследование, полиморфизм, переопределение методов  
**Как опираемся на Тему 1**: Расширяем `Transaction` для поддержки разных типов операций  
**Практика**:
```java
public class Deposit extends Transaction { 
    public Deposit(String id, double amount, String currency) {
        super(id, amount, currency);
    }
}

public class Withdrawal extends Transaction {
    private double balance;
    
    public Withdrawal(String id, double amount, String currency, double balance) {
        super(id, amount, currency);
        this.balance = balance;
    }
    
    @Override
    public boolean isValid() {
        return amount() <= balance; // Проверка баланса
    }
}
```
**Задание**:  
1. Создайте массив `Transaction[]` с разными типами операций  
2. Реализуйте метод `processAll(Transaction[] transactions)`, который:  
   - Проверяет валидность каждой операции  
   - Выводит результат в консоль

---

#### Тема 3: Абстрактные классы и интерфейсы → Выносим общую логику
**Что изучаем**: абстрактные классы, интерфейсы, default-методы  
**Как опираемся на Тему 2**: Устраняем дублирование валидации  
**Практика**:
```java
public interface Validatable {
    boolean isValid();
    
    default void logValidation() {
        System.out.println(isValid() ? "✅ Valid" : "❌ Invalid");
    }
}

public abstract class BaseTransaction implements Validatable {
    private final String id;
    private final double amount;
    private final String currency;
    
    protected BaseTransaction(String id, double amount, String currency) {
        // Общая валидация
        this.id = id;
        this.amount = amount;
        this.currency = currency;
    }
    
    // Геттеры
}
```
**Задание**:  
1. Перепишите `Deposit` и `Withdrawal`, чтобы они наследовали `BaseTransaction`  
2. Добавьте обработку `Transfer` (перевод между счетами)

---

### Неделя 2: Современные фичи (рефакторинг через records и sealed)

#### Тема 4: Records → Заменяем mutable-классы
**Что изучаем**: `record`, неизменяемость, компактные конструкторы  
**Как опираемся на Тему 3**: Упрощаем классы, убирая boilerplate  
**Практика**:
```java
public record Transaction(String id, double amount, String currency) {
    public Transaction {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
    }
}
```
**Задание**:  
1. Перепишите все классы (`Deposit`, `Withdrawal`, `Transfer`) как records  
2. Убедитесь, что валидация теперь в компактных конструкторах

---

#### Тема 5: Sealed Interfaces → Контролируем типы операций
**Что изучаем**: `sealed`, `permits`, безопасность типов  
**Как опираемся на Тему 4**: Заменяем открытую иерархию на строго ограниченную  
**Практика**:
```java
public sealed interface TransactionType permits Deposit, Withdrawal, Transfer {}

public record Deposit(double amount, String currency) implements TransactionType {}
public record Withdrawal(double amount, String currency, double balance) 
    implements TransactionType {}
public record Transfer(double amount, String currency, String targetAccount) 
    implements TransactionType {}
```
**Задание**:  
1. Добавьте новый тип операции `Loan` (реализует `TransactionType`)  
2. Попробуйте создать класс `HackTransaction` вне списка `permits` → убедитесь в ошибке компиляции

---

#### Тема 6: Instanceof + Pattern Matching → Упрощаем обработку типов
**Что изучаем**: `instanceof` с pattern matching, избавление от каскадных if-else  
**Как опираемся на Тему 5**: Обрабатываем sealed-иерархию  
**Практика**:
```java
public String process(TransactionType tx) {
    return switch (tx) {
        case Deposit d -> "Processing deposit: " + d.amount();
        case Withdrawal w -> "Processing withdrawal: " + w.amount();
        case Transfer t -> "Transferring to " + t.targetAccount() + ": " + t.amount();
        // ❌ Ошибка компиляции, если не обработать Loan
    };
}
```
**Задание**:  
1. Добавьте обработку `Loan` в `switch`  
2. Убедитесь, что компилятор **требует обработки всех типов** (попробуйте удалить один `case`)

---

### Неделя 3: Generics и продвинутые паттерны (финальный рефакторинг)

#### Тема 7: Generics → Делаем систему типобезопасной
**Что изучаем**: обобщения, ограничения типов (`T extends`)  
**Как опираемся на Тему 6**: Добавляем поддержку разных типов валют  
**Практика**:
```java
public sealed interface Currency permits USD, EUR, RUB {}
public record USD(double amount) implements Currency {}
public record EUR(double amount) implements Currency {}
public record RUB(double amount) implements Currency {}

public record Transaction<T extends Currency>(String id, T amount) 
    implements TransactionType {}
```
**Задание**:  
1. Перепишите `Deposit` как `Deposit<T extends Currency>`  
2. Создайте `Transaction<USD>` и `Transaction<EUR>`, убедитесь, что `Transaction<String>` не компилируется

---

#### Тема 8: Sealed + Generics + Switch → Замена enum и финальная обработка
**Что изучаем**: комбинация sealed, generics и switch  
**Как опираемся на Тему 7**: Создаем систему ошибок  
**Практика**:
```java
public sealed interface ProcessingResult<T> permits Success, Failure {}
public record Success<T>(T data) implements ProcessingResult<T> {}
public sealed interface Failure permits InsufficientFunds, InvalidCurrency {}
public record InsufficientFunds(String msg) implements Failure {}
public record InvalidCurrency(String currency) implements Failure {}

public ProcessingResult<Double> process(TransactionType tx) {
    return switch (tx) {
        case Deposit d -> new Success<>(d.amount());
        case Withdrawal w when w.amount() <= w.balance() -> 
            new Success<>(w.amount());
        case Withdrawal w -> 
            new InsufficientFunds("Low balance: " + w.balance());
        // ...
    };
}
```
**Задание**:  
1. Добавьте обработку `Transfer` с проверкой баланса  
2. Реализуйте метод `String handleResult(ProcessingResult<?> result)` через `switch`

---

## ✅ Чек-лист навыков
| Навык | Где используется |
|-------|------------------|
| Классы и объекты | Тема 1: `Transaction` |
| Наследование | Тема 2: `Deposit`, `Withdrawal` |
| Абстрактные классы | Тема 3: `BaseTransaction` |
| Records | Тема 4: Замена классов на records |
| Sealed Interfaces | Тема 5: `TransactionType` |
| Pattern Matching | Тема 6: `switch` с обработкой типов |
| Generics | Тема 7: `Transaction<T extends Currency>` |
| Sealed + Generics | Тема 8: `ProcessingResult<T>` |

---

## 💡 Советы по обучению
1. **Не копируйте код** — набирайте вручную для лучшего запоминания
2. **Рефакторьте каждый день** — после изучения новой темы обновляйте предыдущие задания
3. **Тестируйте граничные случаи** — например, попробуйте создать `Withdrawal` с балансом = 0
4. **Используйте IDE** — IntelliJ IDEA подсвечивает ошибки в `switch` для sealed-типов

---

## 🔗 Полезные ресурсы
- [Java 17 Specification](https://docs.oracle.com/javase/specs/jls/se17/html/index.html)
- [Sealed Classes Guide](https://openjdk.org/jeps/409)
- [Records Deep Dive](https://openjdk.org/jeps/395)
- [Pattern Matching for switch (JEP 420)](https://openjdk.org/jeps/420)

