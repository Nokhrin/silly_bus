### **2. Функции и переменные: Базовый синтаксис**
**Цель**: Использовать переменные и методы для решения задач.  
**Задания** (опираются на вывод из п.1):
1. Реализуйте `sum(int a, int b)` и `isEven(int n)`.
2. Напишите итеративный `factorial(int n)`.

**Контрольные вопросы**:
- ❓ Что вернет `sum(Integer.MAX_VALUE, 1)`?
- ❓ Почему `factorial(20)` даст ноль?

**Ссылки**:
- [Variables and Methods (Oracle)](https://docs.oracle.com/javase/tutorial/java/javaOO/methods.html)
- [Java Core для самых маленьких. Часть 3. Переменные](https://habr.com/ru/articles/542480/)
- [](https://ru.hexlet.io/blog/posts/rabota-s-peremennymi-v-java-ob-yavlenie-initsializatsiya-i-oblasti-vidimosti)
- [](https://ru.hexlet.io/courses/java-basics)
- [java code conventions](https://www.oracle.com/technetwork/java/codeconventions-150003.pdf)

*Комментарий*: Все методы — **статические**, как функции в Python. ООП не требуется.


## чему я научился
- понятие `SCREAMING_SNAKE_CASE`
  - применяю для именования констант в Java
- понятие `литерал`
  - по-простецки - выражение, создающее объект определенного типа и с определенным значением
  - пример - `1_000_000_000L` в выражении `long rusRuble = 1_000_000_000L`
- в том же примере `long rusRuble = 1_000_000_000L`:
  - `long` - тип переменной
  - `rusRuble` - имя переменной
  - `=` - оператор присваивания
- `char` представлен натуральными числами 
  - 16 разрядов без знака (16bit unsigned)
  - {175, 92, 95, 40, 12484, 41, 95, 47, 175}
- 