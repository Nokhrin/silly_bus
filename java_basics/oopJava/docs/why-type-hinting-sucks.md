

Type hints are great! But I was playing Devil's advocate on a thread recently where I claimed actually type hinting can be legitimately annoying, especially to old school Python programmers.
> питониста старой школы могут раздражать подсказки типов

But I think a lot of people were skeptical, so let's go through a made up scenario trying to type hint a simple Python package. Go to the end for a TL;DR.
> попробуем аннотировать типы в простом python-пакете

## The scenario

You maintain a popular third party library slowadd, your library has many supporting functions, decorators, classes, and metaclasses, but your main function is:

> ты поддерживаешь библиотеку `slowadd`, содержащую функции, декораторы, классы, метаклассы

функция, выполняющая основную задачу:
```python
def slow_add(a, b):
    time.sleep(0.1)
    return a + b
```

You've always used traditional Python duck typing, if a and b don't add then the function throws an exception. 
> написана по принципу "утиной типизации", если объекты, переданные параметрам a и b не поддерживают сложение, будет поднято исключение

But you just dropped support for Python 2 and your users are demanding type hinting, so it's your next major milestone.
### First attempt at type hinting
> подсказки типов - первый подход

```python
def slow_add(a: int, b: int) -> int:
    time.sleep(0.1)
    return a + b
```

> добавлены указания: a, b принимают объекты типа int (целое число), функция возвращает значение типа int
> после добавления подсказок типов код работает, тесты проходят

All your tests pass, mypy passes against your personal code base, so you ship with the release note "Type Hinting Support added!"


### Second attempt at type hinting

> подсказки типов - второй подход

Users immediately flood your GitHub issues with complaints! MyPy is now failing for them because they pass floats to slow_add, build processes are broken, they can't downgrade because of internal Enterprise policies of always having to increase type hint coverage, their weekend is ruined from this issue.

> у пользователей проблема - статический анализатор, встроенный в конвейер непрерывной интеграции, возвращает ошибку, потому что в пользовательском коде функции передаются числа с плавающей точкой - float, тогда как аннотирован только тип int

You do some investigating and find that MyPy supports Duck type compatibility for ints -> floats -> complex. That's cool! New release:


```python
def slow_add(a: complex, b: complex) -> complex:
    time.sleep(0.1)
    return a + b
```

Funny that this is a MyPy note and not a PEP standard...

> доработка - указан тип, включающий int и float как подмножества
> включение типов int, float в тип complex не является стандартом языка Python, это договоренность, реализованная в конкретном статическом анализаторе


### Third attempt at type hinting

Your users thank you for your quick release, but a couple of days later one user asks why you no longer support Decimal. You replace complex with Decimal but now your other MyPy tests are failing.

You remember Python 3 added Numeric abstract base classes, what a perfect use case, just type hint everything as numbers.Number.

> задача - добавить поддержку объектов класса Decimal
> идея - использовать абстрактный базовый класс Number

> возможная реализация, в статье этого кода нет

```python
from numbers import Number
def slow_add(a: Number, b: Number) -> Number:
    time.sleep(0.1)
    return a + b
```

> ? использование более общего типа данных обесценивает типизацию ?
> типизация применяется для повышения надежности кода, и достигается это снижением вероятности передать объект некорректного типа
> чем шире область определения типа данных, тем выше вероятность передать/получить тип, не предусмотренный реализацией
> от узкого к широкому: int|float -> complex -> Number
> от целого числа или числа с плавающей точкой область допустимых значений расширена до числа любого типа

> в Python каждый класс является наследником класса `object`
> если указать `object` в качестве типа - это будет логически верно

> думаю, такая подсказка типа не повышает надежность кода
```python
def slow_add(a: object, b: object) -> object:
    time.sleep(0.1)
    return a + b
```

Hmmm, MyPy doesn't consider any of integers, or floats, or Decimals to be numbers :(.

After reading through typing you guess you'll just Union in the Decimals:

```python
def slow_add(
        a: Union[complex, Decimal], 
        b: Union[complex, Decimal]
) -> Union[complex, Decimal]:
    time.sleep(0.1)
    return a + b
```
> применен объединяющий тип Union, аргументы и возвращаемое значение могут быть (complex или Decimal)

Oh no! MyPy is complaining that you can't add your other number types to Decimals, well that wasn't your intention anyway...

More reading later and you try overload:

@overload
def slow_add(a: Decimal, b: Decimal) -> Decimal:
...

@overload
def slow_add(a: complex, b: complex) -> complex:
...

def slow_add(a, b):
time.sleep(0.1)
return a + b

> в Python функция есть объект типа callable
> общая практика - callable принимает в качестве аргументов объекты нескольких типов
> возвращаемые функциями значения так же принято не ограничивать одним типом
> такая способность - признак полиморфизма
> для наглядности поддерживаемых типов применяется декоратор overload

```python
import time
from decimal import Decimal
from typing import overload


@overload
def slow_add(a: Decimal, b: Decimal) -> Decimal: ...


@overload
def slow_add(a: int, b: int) -> int: ...


def slow_add(a, b):
    time.sleep(0.1)
    return a + b


if __name__ == '__main__':
    print(slow_add(1, 2.3))
```
> в этом коде статический анализатор найдет ошибку в вызове `print(slow_add(1, 2.3))` - передан объект неожиданного типа - float


But MyPy on strict is complaining that slow_add is missing a type annotation, after reading this issue you realize that @overload is only useful for users of your function but the body of your function will not be tested using @overload. Fortunately in the discussion on that issue there is an alternative example of how to implement:

T = TypeVar("T", Decimal, complex)

def slow_add(a: T, b: T) -> T:
time.sleep(0.1)
return a + b

> анализатор MyPy не распознает перегруженные / overloaded методы, поэтому применены обобщенные классы / generic classe
> то есть объявлен кастомный класс типов, объединяющий Decimal и complex

> как будто решается проблема пройти проверку конкретного статического анализатора
> наверное, это не плохо само по себе, так как MyPy является фактически стандартом статического анализа кода Python,
> невозможность валидировать код в MyPy приводит к блокировке рабочего процесса пользователей
> то есть, приводит к реальной проблеме реальных людей

### Fourth attempt at type hinting

You make a new release, and a few days later more users start complaining. A very passionate user explains the super critical use case of adding tuples, e.g. slow_add((1, ), (2, ))

> появилась задача сложения кортежей
> Python реализует сложение кортежей, сумма кортежей (1, ), (2, ) вернет новый кортеж (1, 2)
> класс tuple наследует object и реализует магический метод __add__, который вызывается оператором +
```python
class tuple(object):
    def __add__(self, value, /)
        Return self+value
```
> магические методы Python - механизм реализации полиморфного поведения
> например, оператор +, примененный к объектам, вызывает магический метод __add__ этих объектов
> "привет " + "мир" -> конкатенация строк -> вернет "привет мир"
> 1 + 2 -> сложение чисел -> вернет 3
> (1, ) + (2, ) -> сложение кортежей -> вернет (1, 2)
> оператор + вызовет метод __add__, если таковой объвлен для объекта

> гибкость за счет снижения предсказуемости?
> 

You don't want to start adding each type one by one, there must be a better way! You learn about Protocols, and Type Variables, and positional only parameters, phew, this is a lot but this should be perfect now:

```python
import time
from typing import Protocol, TypeVar

T = TypeVar("T")  # объявляется тип с названием T, который представляет любой объект Python


class Addable(Protocol):  # объявляется класс-интерфейс - наследуется от Protocol, который является базовым для создания интерфейсов
    def __add__(self: T, other: T, /) -> T: ...  # определяется интерфейс операции +,
    # объект, вызывающий сложение (первый аргумент), может быть любого типа
    # объект, с которым выполняется сложение (второй аргумент), может быть любого типа
    # вовращаемое значение может быть объектом любого типа


def slow_add(a: Addable, b: Addable) -> Addable:
    time.sleep(0.1)
    return a + b


if __name__ == '__main__':
    print(slow_add((1,), (2,)))
```

> интерфейс - это описание поведения объекта без указания конкретной реализации

#### на примере платежной операции

```python
from typing import Protocol, TypeVar

T = TypeVar("T")


class FinancialOperationsDraft.Operation(Protocol[T]):
    """Операция - интерфейс."""
    def perform(self) -> T: ...  # метод не возвращает значение явно, но скрыто вернет None


def process_operation(operation: FinancialOperationsDraft.Operation[T]):
    operation.perform()


class FinancialOperationsDraft.Deposit:
    """Реализация интерфейса FinancialOperationsDraft.Operation."""
    def __init__(self, amount: float, account_id: str):
        self._amount = amount
        self._account_id = account_id

    def perform(self) -> None:
        """Выполняет пополнение счёта. Возвращает None."""
        print(f'зачисляю {self._amount} на счет {self._account_id}')


if __name__ == '__main__':
    deposit = FinancialOperationsDraft.Deposit(amount=1000.50, account_id="ACC1")
    result = process_operation(deposit)
    print(result)  # None
```

> в классе FinancialOperationsDraft.Deposit нет прямого указания на связь с классом-интерфейсом FinancialOperationsDraft.Operation
> связь - единая сигнатура метода perform
> связь - не по наследованию, 
>   FinancialOperationsDraft.Deposit не наследует класс FinancialOperationsDraft.Operation
> связь - по структуре
>   FinancialOperationsDraft.Deposit определяет метод, который определен в FinancialOperationsDraft.Operation
> статические анализаторы кода проверяют соответствие по структуре, не учитывают наследование

`class FinancialOperationsDraft.Operation(Protocol[T])` - объявление класса, наследующего Protocol, понимается стат анализатором как источник информации для проверки интерфейса
```python
class FinancialOperationsDraft.Operation(Protocol[T]):
    def perform(self) -> T: ...
```
определяет, что каждый класс, реализующий метод perform, соответствует интерфейсу / реализует интерфейс FinancialOperationsDraft.Operation

порядок проверки
```python
class FinancialOperationsDraft.Deposit:
    def perform(self) -> None:
        ...
```
1. есть метод perform
2. сигнатура метода FinancialOperationsDraft.Deposit.perform соответствует сигнатуре метода FinancialOperationsDraft.Operation.perform
   - так как тип None входит в множество типа кастомного типа T
3. вывод - FinancialOperationsDraft.Deposit реализует FinancialOperationsDraft.Operation, реализация валидна

---

A mild diversion

You make a new release noting "now supports any addable type".

Immediately the tuple user complains again and says type hints don't work for longer Tuples: slow_add((1, 2), (3, 4)). That's weird because you tested multiple lengths of Tuples and MyPy was happy.

After debugging the users environment, via a series of "back and forth"s over GitHub issues, you discover that pyright is throwing this as an error but MyPy is not (even in strict mode). You assume MyPy is correct and move on in bliss ignoring there is actually a fundamental mistake in your approach so far.

> обнаружено, что проверка типов в некоторых случаях выполняется с ошибкой
> формально с точки зрения стат анализатора реализация корректна
> но появляется ощущение фундаментальной пролемы в подходе

> я вижу проблему в том, что такое аннотирование не дает гарантии проверки типа передаваемого/возвращаемого объекта

(Author Side Note - It's not clear if MyPy is wrong but it defiantly makes sense for Pyright to throw an error here, I've filed issues against both projects and a pyright maintainer has explained the gory details if you're interested. Unfortunately this was not really addressed in this story until the "Seventh attempt")

### Fifth attempt at type hinting

A week later a user files an issue, the most recent release said that "now supports any addable type" but they have a bunch of classes that can only be implemented using __radd__ and the new release throws typing errors.

> `__add__`, `__radd__` - публичные методы класса, выполняющие операцию сложения
>  `__add__` выполняется, когда экземпляр класса, в котором опеределен `__add__`, предшествует оператору "+"
>  `__radd__` выполняется, когда экземпляр класса, в котором опеределен `__radd__`, следует за оператором "+"

```python
class FinancialOperationsDraft.Account:
    """Счет."""
    def __init__(self, balance: int, account_id: str):
        self._balance = balance
        self._stash = 0
        self._account_id = account_id

    def __add__(self, amount):
        if isinstance(amount, int):
            self._balance += amount
        else:
            raise ValueError('сумма может быть int')

    def __radd__(self, amount):
        """Заначка."""
        if isinstance(amount, int):
            self._stash += amount
        else:
            raise ValueError('сумма может быть int')

    def __repr__(self):
        return f'баланс: {self._balance}\nзаначка: {self._stash}\n'


if __name__ == '__main__':
    acc = FinancialOperationsDraft.Account(balance=0, account_id="ACC1")

    acc + 100
    print(acc)
    # баланс: 100
    # заначка: 0

    50 + acc
    print(acc)
    # баланс: 100
    # заначка: 50
```

> проблема, обозначенная в тексте, - у пользователей, в классах которых реализован метод `__radd__` происходит ошибка при проверке типов метода `slow_add`

> проверка

You try a few approaches and find this seems to best solve it:
> решение

```python
class Addable(Protocol):
    def __add__(self: T, other: T, /) -> T: ...

class RAddable(Protocol):
    def __radd__(self: T, other: Any, /) -> T: ...

@overload
def slow_add(a: Addable, b: Addable) -> Addable: ...

@overload
def slow_add(a: Any, b: RAddable) -> RAddable: ...

def slow_add(a: Any, b: Any) -> Any:
    time.sleep(0.1)
    return a + b
```

Annoyingly there is now no consistent way for MyPy to do anything with the body of the function. Also you weren't able to fully express that when b is "RAddable" that "a" should not be the same type because Python type annotations don't yet support being able to exclude types.
### Sixth attempt at type hinting

A couple of days later a new user complains they are getting type hint errors when trying to raise the output to a power, e.g. pow(slow_add(1, 1), slow_add(1, 1)). Actually this one isn't too bad, you quick realize the problem is your annotating Protocols, but really you need to be annotating Type Variables, easy fix:

T = TypeVar("T")

class Addable(Protocol):
def __add__(self: T, other: T, /) -> T:
...

A = TypeVar("A", bound=Addable)

class RAddable(Protocol):
def __radd__(self: T, other: Any, /) -> T:
...

R = TypeVar("R", bound=RAddable)

@overload
def slow_add(a: A, b: A) -> A:
...

@overload
def slow_add(a: Any, b: R) -> R:
...

def slow_add(a: Any, b: Any) -> Any:
time.sleep(0.1)
return a + b

### Seventh attempt at type hinting

Tuple user returns! He says MyPy in strict mode is now complaining with the expression slow_add((1,), (2,)) == (1, 2) giving the error:

    Non-overlapping equality check (left operand type: "Tuple[int]", right operand type: "Tuple[int, int]")

You realize you can't actually guarantee anything about the return type from some arbitrary __add__ or __radd__, so you starting throwing Any Liberally around:

class Addable(Protocol):
def __add__(self: "Addable", other: Any, /) -> Any:
...

class RAddable(Protocol):
def __radd__(self: "RAddable", other: Any, /) -> Any:
...

@overload
def slow_add(a: Addable, b: Any) -> Any:
...

@overload
def slow_add(a: Any, b: RAddable) -> Any:
...

def slow_add(a: Any, b: Any) -> Any:
time.sleep(0.1)
return a + b

### Eighth attempt at type hinting

Users go crazy! The nice autosuggestions their IDE provided them in the previous release have all gone! Well you can't type hint the world, but I guess you could include type hints for the built-in types and maybe some Standard Library types like Decimal:

You think you can rely on some of that MyPy duck typing but you test:

@overload
def slow_add(a: complex, b: complex) -> complex:
...

And realize that MyPy throws an error on something like slow_add(1, 1.0).as_integer_ratio(). So much for that nice duck typing article on MyPy you read earlier.

So you end up implementing:

class Addable(Protocol):
def __add__(self: "Addable", other: Any, /) -> Any:
...

class RAddable(Protocol):
def __radd__(self: "RAddable", other: Any, /) -> Any:
...

@overload
def slow_add(a: int, b: int) -> int:
...

@overload
def slow_add(a: float, b: float) -> float:
...

@overload
def slow_add(a: complex, b: complex) -> complex:
...

@overload
def slow_add(a: str, b: str) -> str:
...

@overload
def slow_add(a: tuple[Any, ...], b: tuple[Any, ...]) -> tuple[Any, ...]:
...

@overload
def slow_add(a: list[Any], b: list[Any]) -> list[Any]:
...

@overload
def slow_add(a: Decimal, b: Decimal) -> Decimal:
...

@overload
def slow_add(a: Fraction, b: Fraction) -> Fraction:
...

@overload
def slow_add(a: Addable, b: Any) -> Any:
...

@overload
def slow_add(a: Any, b: RAddable) -> Any:
...

def slow_add(a: Any, b: Any) -> Any:
time.sleep(0.1)
return a + b

As discussed earlier MyPy doesn't use the signature of any of the overloads and compares them to the body of the function, so all these type hints have to manually validated as accurate by you.
### Ninth attempt at type hinting

A few months later a user says they are using an embedded version of Python and it hasn't implemented the Decimal module, they don't understand why your package is even importing it given it doesn't use it. So finally your code looks like:

from __future__ import annotations

import time
from typing import TYPE_CHECKING, Any, Protocol, TypeVar, overload

if TYPE_CHECKING:
from decimal import Decimal
from fractions import Fraction


class Addable(Protocol):
def __add__(self: "Addable", other: Any, /) -> Any:
...

class RAddable(Protocol):
def __radd__(self: "RAddable", other: Any, /) -> Any:
...

@overload
def slow_add(a: int, b: int) -> int:
...

@overload
def slow_add(a: float, b: float) -> float:
...

@overload
def slow_add(a: complex, b: complex) -> complex:
...

@overload
def slow_add(a: str, b: str) -> str:
...

@overload
def slow_add(a: tuple[Any, ...], b: tuple[Any, ...]) -> tuple[Any, ...]:
...

@overload
def slow_add(a: list[Any], b: list[Any]) -> list[Any]:
...

@overload
def slow_add(a: Decimal, b: Decimal) -> Decimal:
...

@overload
def slow_add(a: Fraction, b: Fraction) -> Fraction:
...

@overload
def slow_add(a: Addable, b: Any) -> Any:
...

@overload
def slow_add(a: Any, b: RAddable) -> Any:
...

def slow_add(a: Any, b: Any) -> Any:
time.sleep(0.1)
return a + b

TL;DR

Turning even the simplest function that relied on Duck Typing into a Type Hinted function that is useful can be painfully difficult.

Please always put on your empathetic hat first when asking someone to update their code to how you think it should work.

In writing up this post I learnt a lot about type hinting, please try and find edge cases where my type hints are wrong or could be improved, it's a good exercise.

Edit: Had to fix a broken link.

Edit 2: It was late last night and I gave up on fixing everything, some smart people nicely spotted the errors!

### I have a "tenth attempt" to address these error. But pyright complains about it because my overloads overlap, however I don't think there's a way to express what I want in Python annotations without overlap. Also Mypy complains about some of the user code I posted earlier giving the error comparison-overlap, interestingly though pyright seems to be able to detect here that the types don't overlap in the user code. 

I'm going to file issues on pyright and mypy, but fundamentally they might be design choices rather than strictly bugs and therefore a limit on the current state of Python Type Hinting:

T = TypeVar("T")

class SameAddable(Protocol):
def __add__(self: T, other: T, /) -> T:
...

class Addable(Protocol):
def __add__(self: "Addable", other: Any, /) -> Any:
...

class SameRAddable(Protocol):
def __radd__(self: T, other: Any, /) -> T:
...

class RAddable(Protocol):
def __radd__(self: "RAddable", other: Any, /) -> Any:
...

SA = TypeVar("SA", bound=SameAddable)
RA = TypeVar("RA", bound=SameRAddable)


@overload
def slow_add(a: SA, b: SA) -> SA:
...

@overload
def slow_add(a: Addable, b: Any) -> Any:
...

@overload
def slow_add(a: Any, b: RA) -> RA:
...

@overload
def slow_add(a: Any, b: RAddable) -> Any:
...

def slow_add(a: Any, b: Any) -> Any:
time.sleep(0.1)
return a + b

