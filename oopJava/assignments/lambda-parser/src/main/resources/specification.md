# Функциональный парсер-комбинатор

## Требования

```ebnf
integer {[whitespace] binary_operator [whitespace] integer}
```

## Атомарные парсеры
1. [Парсер integer](../java/lambda_parser/IntParser.java)
2. [Парсер whitespace](../java/lambda_parser/WhitespaceParser.java)
3. [Парсер binary_operator](../java/lambda_parser/BinaryOperatorParser.java)

## Комбинаторы
1. [Структура Combined](../java/lambda_parser/CombinedImpl.java)
2. [Структура Suffix](../java/lambda_parser/SuffixImpl.java)
3. [Парсер выражения](../java/lambda_parser/ExpressionParser.java)
4. [Повторитель парсера](../java/lambda_parser/ListParser.java)

---

### Стартовая постановка

<details><summary>развернуть</summary>



Парсинг и парсеры представлены как функции с определенной сигнатурой:

```java
Optional<ParseResult<A>> parse(String source, int begin_offset);

interface ParseResult<A> {
    A value();

    int end_offset();
}
```

---

Теперь должен быть
```java
interface Parser<А> {
    Optional<ParseResult<A>> parse(String source, int begin_offset);
}
```

Данный интерфейс по факту можно условно назвать лямбдой (1 метод), его называют
функциональным, но это только в Java



---


## Синтаксис
вида:
`integer {[whitespace] binary_operator [whitespace] integer}`

Это должен быть комбинированный объект Parser, который содержит другие объекты
Parser.

В результате этого синтаксиса должен быть объект вида (условный синтаксис)

```java
interface combined {
    Integer head();
    List<Suffix> tail();
}

interface Suffix {
    BinaryOperator operator();
    Integer value();
}
```

## Запрограммировать базовые части

1. Класс реализующий Parser< Integer>

2. класс для Parser < Whitespace>

3. класс для Parser < BinaryOperator>
   BinaryOperator это enum {add, sub, div, mul} - мат операции

4. Класс реализующ ий Parser< List< A> > ,
   который:
- в конструкторе принимает Parser< А>

- применяет принятый парсер для парсинга повторов во входных данных ( source,
  offset)
- Возвращает в случае совпадения результат.
- Можно указать Минимальное /максимальное кол- во повторов (min, max)
- Работает по „жадному" алгоритму.
  Данный Parser < List < A > > является частю синтаксиса E BNF
  В случае min = 0 & max = 1, это соответствует квадратным скобками в eBNF
  В случае min = 0 & max > 0 - фигурным скобкам еBNF

5. Операцию Мар/FlatМар для Parser и
   ParserResult
   Операция Мар замена содержимого контейнера.
   Операция должна порождать новый контейнер.
   В статически типизируемых языках операция Мар меняет тип содержимого:
   пример: List < String> на List < Boolean>
   Операция Мар применяется к каждому элементу Function< A,B> , кол- во элементов
   контейнер остается не изменным

Контейнер может быть любым List, Optional,....

условно map выглядит так:
   < A,B> List< В> mapList(List< А> list, Function< А,В> f)
   Операция Flat Map выполняет аналогичную функцию, за исключение ограничения на
   кол- во элементов в контейнере результата
   < A,B> List< B> flatMapList(List< А> list, Function< А, List< В> > f)
   Лямбда f принимает элемент и должна возвращать какое - то кол- во элементов другого
   типа 0+

Лямбда f не обязательно должна возвращать List, зависит от требований

Используя Flat map можно как фильтровать/уменьшать содержимое исходного
   контейнера, так и увеличивать

Необходимо реализовать map для
- ParseResult
- Parser
- 

  interface ParseResult< А> {
  ...
  default < В> ParseResult< B> map ( Function< A,B> f)
  }

- interface Parser < А> {
  ...
  default < В> Parser < B> map ( Function< A,B> f)
  }

- Необходимо реализовать flatmap для
- Parser
  interface Parser < А> {
  ...
  default < В> Parser < B> flatmap ( Function< A,Optional< B> > f)
  }

6. Реализовать кортеж
   Кортеж типизированная коллекция, где каждый элемент имеет свой тип. Обычно такая
   коллекция не допускает бесконечное кол- во элементов.
   Кортеж из 2х элементов
```java
   interface Тuple2< А,В> {
   A a();
   В b();
   }
```
7. Операция sequence/ + в еBNF
   В синтаксисе eBNF есть конструкция когда за одной частю следует другая, пример:
   digit letter
   Тут после цифры (digit) должна следовать буква (letter)
   Допустим есть 2 парсера:
   Parser< Digit>
   Parser< Letter>
   Для digit letter должен существовать Parser< Тuple2< Digit,Letter> >
   Необходимо создать операцию + для Parser
   interface Parser < А> {
   ...
   default < В>
   Parser< Тuple2< A,B> > plus( Parser< B> p)
   }
8. Операция repeat для еBNF
   На шаге 4 уже описывался синтаксис {} и [], теперь эти операции должны
   быть выражены в Parser
   interface Parser < А> {
   ...
   default
   Parser< List< A> >
   repeat (int min, int max)
   default
   Parser< Optional< А> >
   optional()
   }
9. Сборка парсеров в единый объект
   Теперь собрать парсер
   integer { binary_operator integer}
   Допустим есть уже парсеры:
   Parser < Integer> intР = ...
   Parser < Whitespace> wsP =...
   Parser < BinaryOperator> binР =...
   Начать с простых конструкций:
   integer binary_operator
   Результат такого простого парсера record R1(Integer n, Binaryoperator о)
   Должно быть примерно так
   intP.plus( binP ).map( tuple - > new R1( tuple.a(), tuple.b() ) )
   После по пробовать собрать такую конструкцию
   integer { binary_operator integer}
10. Доработать операцию +
    interface Parser < А> {
    ...
    default < В>
    Plus< A,B> plus( Parser< B> p)
    }
    где Plus это
    interface Plus< A,В> extends Parser< Тuple2 < A,B> > {
    Parser< А> skipRight();
    Parser< В> skipLeft();
    }
    Skip Left, Right отбрасываем через map значение (например whitespace)
11. Реализовать контейнер Или (Either)
    interface Either< А,В> {
    static < A,В> Either< А,В> left(A a, Class< В> b)
    static < A,В> Either< А,В> right (B b, Class< A > а)
    Optional< А> getLeft();
    Optional< В> getRight();
    }
    Контейнер хранит строго либо одно, либо другое значение.
    Контейнер не может не хранить значений
    Контейнер не может хранить оба значения одновременно
12. Реализовать операцию Или в eBNF.
    interface Parser < А> {
    ...
    default < В>
    Parser< Eitherrj < A,B> >
    or( Parser< B> p)
    }
13. Попровать совместить Или и +
    [whitespace] binary_operator
    Такой синтаксис может быть переписан с сохранением результата в виде:
    whitespace binary_operator | binary_operator
    Оба синтаксиса являются равнозначными
14. Реализовать целевой синтаксис
    integer {[whitespace] binary_operator [whitespace] integer}
    В результате этого синтаксиса должен быть объект вида (условный синтаксис)
    interface {
    Integer head ();
    List< Suffix> tail ();
    }
    interface Suffix {
    BinaryOperator operator ();
    Integer value ();
    }



</details>
