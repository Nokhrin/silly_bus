Минимальный базисис Java
=======================================

Требования к gpt/qwen/grok

> план должен быть практичный, с заданиями, с каверзными контрольными вопросами
>
> задания должны быть точными и результат контролируемым
>
> Каждая следующая тема и задача должна операться на достигнутые результаты
>
> должен быть какой-то итоговый проект или результат этих задач, который суммирует полученный опыт
>
> приведи примеры решений
> к каждой теме по мимо ссылок, должен быть пример решения
> 
> темы должны по минимому затрагивать вопросы ООП, это часть другой практики
> только там, где без этого не обойтись - работа с файлами
> 
> разбор конструкции try catch в этом случае необходим, но не надо требовать понимания иерархии ошибок и их написания
> если можещь обойтись просто Map<?,?> , List<?> укажи что данная тема затрагивает ООП, но не является объязательно и требования к ООП, просто запомнить синтаксис, без деталей
> 
> только процедурное программирование требуется

--------------------

## Общие требования/рекомендации к решению задач

- по возможности использовать maven и unit test.
  - конструкцию языка assert, а использовать assert _NNNN_() функции взамен
- при написании функций и методов
  - требование к публичным: дать комментарий (javadoc) зачем эта функция
  - рекомендация: дать внятное название для приватных или комментарий, так что бы не смотря на реализацию было понятно как ее ползоваться
  - требование: реализация не должна пологатся на скрытые знания (например на условия задачи или внешнюю переписку)
     - знания или требования к использованию функций должны быть явно в коде задокументированы, например javadoc
        - для приватных - не важно
        - для остальных - важно, требование
     - для статических методов: реализация должна пологатся исключительно на типы данных которые объявлены в аргументах функции
       - исключить использование глобальных переменных и других данных которые вне тела функции
     - для виртуальных методов: реализация должна пологатся на типы данных которые объявлены в аргументах метода и допускается использование полей класса
       - исключить использование глобальных переменных и других данных которые вне тела функции или класса
     - исключение: если по условию задачи требуется явное использование внешних данных
     - примеры:
       - в метод some( String a ) передается аргумент a, тип объект класса String
         - по скольку a это объект, а не примитив, то есть передается по ссылке - следовательно возможно передача null ссылки - такие случаи проверять
         - при не допустимых значениях выкидывать исключение IllegalArgumentException
         - эти допустимые и не допустимые значения для public методов должны быть явно проговорены в javadoc
           - возможно исключение для null, текущая практика программирования java обычно всегда передает действительные ссылки, и это стало по умолчанию
       - в метод some( int weight ) передается вес, вес не может быть отрицательным, а числа int могут - требуется проверка
   - рекомендация на грани требования (из области функционального программирования): не использовать аргументы как возвращаемые значения
     - речь о том, что в аргументах можно передать объект, который внутри метода можно изменить, а измененое значение использовать как результат работы метода
     - результат должен возвращаться через return
     - исключение: если название метода четко определяет суть, например configure( Printer printer, String parameter, String value ) - и то... _такое себе оправдание_

## **Обновленный практический план обучения Java: процедурный подход с минимумом ООП**  
Все темы сфокусированы на **процедурном программировании**. ООП упоминается **только где неизбежно** (коллекции, файлы), но без деталей — достаточно запомнить синтаксис. Для каждой темы (кроме итоговой) добавлены **примеры решений** и **проверенные ссылки**.


---

### **1. Hello World: Запуск программы и структура кода**  
**Цель**: Научиться компилировать и запускать программу, понять роль `main`.  
**Задания**:  
1. Создайте `Main.java`, выведите `Привет, [Имя]! Сегодня [число] урок Java.`  
2. Добавьте вывод длины имени и сумму `7 + 3`.  

**Контрольные вопросы**:  
- ❓ Что выведет `System.out.print("A"); System.out.println("B");`?  
- ❓ Почему `main` должен быть `public static void`?  

**Ссылки**:  
- [Java Hello World (Oracle)](https://docs.oracle.com/javase/tutorial/getStarted/application/index.html)  
- Установка JDK, Idea
  - https://javarush.com/quests/lectures/questsyntaxpro.level20.lecture00
  - https://skillbox.ru/media/base/kak_ustanovit_jdk_i_sredu_razrabotki_intellij_idea/
- база https://ru.hexlet.io/courses/java-basics


**Пример решения**:  
```java
public class Main {
    public static void main(String[] args) {
        String name = "Alex";
        int lessonNumber = 1;
        System.out.println("Привет, " + name + "! Сегодня " + lessonNumber + " урок Java.");
        System.out.println("Длина имени: " + name.length());
        System.out.println("7 + 3 = " + (7 + 3));
    }
}
```
*Комментарий*: Здесь `String` и `.length()` — часть ООП, но для задачи достаточно знать синтаксис, как калькулятор.

---

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
- https://habr.com/ru/articles/542480/
- https://ru.hexlet.io/blog/posts/rabota-s-peremennymi-v-java-ob-yavlenie-initsializatsiya-i-oblasti-vidimosti
- база https://ru.hexlet.io/courses/java-basics

**Пример решения**:  
```java
public class Main {
    public static void main(String[] args) {
        System.out.println("Сумма 5 + (-3) = " + sum(5, -3));
        System.out.println("4 четное? " + isEven(4));
        System.out.println("Факториал 5 = " + factorial(5));
    }

    public static int sum(int a, int b) {
        return a + b;
    }

    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    public static int factorial(int n) {
        int result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
```
*Комментарий*: Все методы — **статические**, как функции в Python. ООП не требуется.

---

[3. Списки и карты: Работа с коллекциями](src/main/java/pop/lesson03/README.md)


### **4. Ввод/вывод с консоли: Работа с пользователем**  
**Цель**: Собирать данные от пользователя и **обрабатывать ошибки**.  
**Задания** (опираются на коллекции из п.3):  
1. Запросите имя и возраст, сохраните в `Map`.  
2. Обработайте ошибку ввода не-числа через `try/catch`.  

**Контрольные вопросы**:  
- ❓ Что произойдет при вводе `abc` в `Scanner.nextInt()`?  
- ❓ Как считать строку с пробелами (`"New York"`) через `Scanner`?  

**Ссылки**:  
- [Scanner Class (Oracle)](https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html)  
- https://metanit.com/java/tutorial/2.9.php
- https://otus.ru/nest/post/1673/


**Пример решения**:  
```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> people = new HashMap<>();

        System.out.print("Введите имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите возраст: ");
        try {
            int age = scanner.nextInt();
            people.put(name, age);
            System.out.println("Данные сохранены!");
        } catch (Exception e) {
            System.out.println("Ошибка: введите число!");
            scanner.next(); // Очистка буфера
        }

        System.out.println("Текущие данные: " + people);
    }
}
```
*Комментарий*: `try/catch` — базовая обработка ошибок (как `try/except` в Python). Иерархия исключений не требуется.

#### Доп задача - типичный цикл диалога

1) "баннер"
2) приглашение ввода
3) ввод в цикле
4) отдельная команда завершения ввода

это типичная структура диалога программы с пользователем

**1) "баннер"**

"баннер" - некий текст при запуске программы

**2) приглашение ввода**

**please enter>** _abc_

please enter> - это текст приглашения

abc - это уже пользователь вводит

все это в одну строку - смотри flush() для OutputStream / System.out что бы вывести в одно строку приветствие и ввод пользователя

**3,4)**

цикл и завершение цикла - что за пользовательский ввод, который завершит выполнение - на твой выбор

может быть как введенная стока "exit", так и просто пустая строка - как выберешь

задача - завести значения в `Map<String,Integer>`

заводим в диалоговом режиме с пользователем

а потом например подсчитываем какую ни будь статистику (средний возраст, частое имя или еще что нибудь)

**Комментарии**

> я к тому, чтобы обрабатывать EOF как завершение ввода, а не как завершение программы или так не делают?
>> не... так не надо
у тебя либо интерактивный режим - т.е. диалог с пользователем
либо автономный, тогда конец входных данных, аля EOF

>>интерфейс пользователя - представь что ты первый раз запустил программу - из ее ответов должно быть понятно как дальше с ней работать
а диалоговый режим - вопрос - ответ в цикле
из ответов должно быть понятно куда двигаться


---

### **5. Отладка: Поиск ошибок**  
**Цель**: Использовать дебаггер для анализа кода из п.4.  
**Задания**:  
1. Поставьте breakpoint в `try/catch` (п.4), проверьте изменение переменных.  

**Ссылки**:  
- [Debugging in IntelliJ (JetBrains)](https://www.jetbrains.com/help/idea/debugging-code.html)  

**Пример решения**:  
- Запустите программу в дебаггере, поставьте breakpoint на `int age = scanner.nextInt();`.  
- При вводе `abc` увидите:  
  - Переменная `e` содержит текст ошибки.  
  - Выполнение переходит в `catch` блок.  

---

### **6. Создание дистрибутива**  
**Цель**: Собрать программу из п.4 в `jar`.  
**Задания**:  
1. Скомпилируйте и соберите в `jar` программу с вводом имени и возраста.  

**Ссылки**:  
- [Building JAR Files (Oracle)](https://docs.oracle.com/javase/tutorial/deployment/jar/build.html)  

**Пример решения**:  
```bash
javac Main.java
jar cfe myapp.jar Main Main.class
java -jar myapp.jar
```

---

### **7. Maven: Управление зависимостями**  
**Цель**: Подключить внешние библиотеки.  
**Задания**:  
1. Используйте `StringUtils.capitalize()` из `commons-lang3`.  

**Ссылки**:  
- [Maven in 5 Minutes (Apache)](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)  
- https://github.com/gochaorg/blog/tree/master/itdocs/maven

**Пример решения**:  
```java
// pom.xml
<dependencies>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.12.0</version>
    </dependency>
</dependencies>

// Main.java
import org.apache.commons.lang3.StringUtils;

public class Main {
    public static void main(String[] args) {
        System.out.println(StringUtils.capitalize("alex")); // Alex
    }
}
```

---

### **8. Работа с файлами: Сохранение данных между запусками**  
**Цель**: Читать/записывать файлы, **опираясь на обработку ошибок из п.4**.  
**Задания** (используют Map из п.3 и try/catch из п.4):  
1. Запишите данные из `Map` в `tasks.csv`.  
2. Обработайте `FileNotFoundException` через `try/catch`.  

**Контрольные вопросы**:  
- ❓ Почему `FileWriter` без `true` перезаписывает файл?  
- ❓ Как безопасно закрыть `BufferedReader`?  

**Ссылки**:  
- [Java I/O Tutorial (Oracle)](https://docs.oracle.com/javase/tutorial/essential/io/)
- https://metanit.com/java/tutorial/6.11.php
- https://ru.hexlet.io/qna/java/questions/kak-chitat-iz-fayla-java
- Потоки ввода, InputStream https://java-online.ru/java-inputstream.xhtml
- Потоки вывода, OutputStream https://java-online.ru/java-outputstream.xhtml
- Потоки Reader и Writerhttps://java-online.ru/java-reader.xhtml
- [Электронная библиотека БГУ / Java. Потоки ввода-вывода. Работа с файлами. Кравчук А ...](https://elib.bsu.by/bitstream/123456789/294799/1/Java.%20%D0%9F%D0%BE%D1%82%D0%BE%D0%BA%D0%B8%20%D0%B2%D0%B2%D0%BE%D0%B4%D0%B0-%D0%B2%D1%8B%D0%B2%D0%BE%D0%B4%D0%B0.%20%D0%A0%D0%B0%D0%B1%D0%BE%D1%82%D0%B0%20%D1%81%20%D1%84%D0%B0%D0%B9%D0%BB%D0%B0%D0%BC%D0%B8.%20%D0%9A%D1%80%D0%B0%D0%B2%D1%87%D1%83%D0%BA%20%D0%90.%D0%A1.%2C%20%D0%9A%D1%80%D0%B0%D0%B2%D1%87%D1%83%D0%BA%20%D0%90.%D0%98.%2C%20%D0%9A%D1%80%D0%B5%D0%BC%D0%B5%D0%BD%D1%8C%20%D0%95.%D0%92_.pdf)

**Пример решения**:  
```java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Integer> people = new HashMap<>();
        people.put("Alex", 25);
        people.put("Maria", 30);

        // Запись в файл (try/catch как в п.4)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.csv"))) {
            for (String name : people.keySet()) {
                writer.write(name + "," + people.get(name));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи: " + e.getMessage());
        }

        // Чтение из файла
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден. Создайте его сначала.");
        } catch (IOException e) {
            System.err.println("Ошибка чтения: " + e.getMessage());
        }
    }
}
```
*Комментарий*:  
- `try-with-resources` автоматически закрывает файлы (без деталей ООП).  
- Обработка ошибок **такая же, как в п.4** — только `IOException` вместо `InputMismatchException`.

---

### **9. Итоговый проект: Консольный трекер задач**  
**Цель**: Объединить все навыки в приложении с сохранением состояния.  

#### **План решения и обоснование**  
1. **Структура данных** (процедурный стиль):  
   - Используем `List<Map<String, String>>` вместо класса `Task` (как в п.3).  
   *Обоснование*: Избегаем ООП, работаем с коллекциями как в п.3.  

2. **Ввод/вывод и обработка ошибок** (п.4, п.8):  
   - Меню через `Scanner` (п.4).  
   - Сохранение в `tasks.csv` через `BufferedWriter` (п.8).  
   *Обоснование*: Повторяем шаблоны из п.4 и п.8, но объединяем их.  

3. **Сборка** (п.6, п.7):  
   - Сборка через Maven (п.7) для упрощения запуска.  

#### **Пример структуры кода**  
```java
import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_NAME = "tasks.csv";

    public static void main(String[] args) {
        List<Map<String, String>> tasks = loadTasks(); // Из п.8
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int choice = getValidIntInput(scanner); // Обработка ошибок из п.4

            if (choice == 1) {
                addTask(tasks, scanner);
                saveTasks(tasks); // Из п.8
            } else if (choice == 2) {
                printTasks(tasks);
            } else if (choice == 3) {
                break;
            }
        }
    }

    // Методы из п.3, п.4, п.8
    private static List<Map<String, String>> loadTasks() { ... }
    private static void saveTasks(List<Map<String, String>> tasks) { ... }
    private static int getValidIntInput(Scanner scanner) { ... }
}
```

#### **Критерии проверки**  
- При запуске `java -jar tracker.jar` выводится меню.  
- Данные сохраняются в `tasks.csv` и загружаются при повторном запуске.  
- Обработка ошибок: некорректный ввод числа, отсутствие файла.  

#### **Каверзные вопросы**  
- ❓ Что произойдет, если в `tasks.csv` будет строка `Buy milk,high` (без статуса)?  
  *Ответ*: Программа пропустит строку (как в п.8 при `FileNotFoundException`).  
- ❓ Почему `try-with-resources` безопаснее ручного закрытия файла?  
  *Ответ*: Гарантирует закрытие даже при ошибках (как в п.4).  

---

### **Почему это работает**  
1. **Процедурный фокус**:  
   - Все методы — **статические**, без классов (кроме `main`).  
   - Коллекции (`List`, `Map`) используются как структуры данных (без ООП-деталей).  

2. **Последовательность**:  
   - Обработка ошибок ввода (п.4) → обработка ошибок файлов (п.8) → итоговый проект.  
   - Файловые операции (п.8) строятся на `try/catch` из п.4.  

3. **Минимум ООП**:  
   - Упоминание `List`/`Map` только как синтаксис (как списки/словари в Python).  
   - Нет классов, наследования, инкапсуляции — только коллекции и обработка ошибок.  



