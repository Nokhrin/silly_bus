# Полиморфизм

Выбор реализации на основе динамического типа объекта в runtime
Усиление абстракции - множество реализаций внутри класса, извне доступен универсальный интерфейс

```java
import java.math.BigDecimal;

/**
 * Передаю число как значение типа, допускаемого описанием/контрактом интерфейса
 */
class Demo() {
    public static void main(String[] args) {
        BigDecimal bigDecimal1 = new BigDecimal(1);
        BigDecimal bigDecimal2 = new BigDecimal("1");
        BigDecimal bigDecimal3 = new BigDecimal(1.0);
    }
}
```