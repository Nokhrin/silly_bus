Базис Java
=======================================

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

*Комментарий*: Все методы — **статические**, как функции в Python. ООП не требуется.


---


<details><summary><b>
следующие темы
</b></summary>
---

### **3. Списки и карты: Работа с коллекциями**  
**Цель**: Хранить данные в `List` и `Map` (без углубления в ООП).  
**Задания** (используют методы из п.2):  
1. Найдите максимум и среднее в списке `[3, 1, 4, 1, 5]`.  
2. Создайте `Map<String, Integer>` для пар «имя → возраст».  

**Контрольные вопросы**:  
- ❓ Почему `map.get("Alex")` вернет `null` для ключа `"alex"`?  
- ❓ Как удалить дубликаты через `new HashSet<>(list)`?  

**Ссылки**:  
- [Collections Tutorial (Oracle)](https://docs.oracle.com/javase/tutorial/collections/)  
- https://javarush.com/groups/posts/2308-korotko-o-glavnom---java-collections-framework
- https://skillbox.ru/media/code/gotovimsya_k_sobesedovaniyu_chto_nuzhno_znat_o_kollektsiyakh_v_java/
- https://struchkov.dev/blog/ru/java-collection-framework/


<details><summary><b>
Пример решения
</b></summary>

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // List (часть ООП, но синтаксис как в Python)
        List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5);
        int max = Collections.max(numbers);
        double avg = numbers.stream().mapToInt(i -> i).average().orElse(0);

        // Map (синтаксис как словарь в Python)
        Map<String, Integer> people = new HashMap<>();
        people.put("Alex", 25);
        people.put("Maria", 30);

        System.out.println("Максимум: " + max + ", Среднее: " + avg);
        System.out.println("Люди старше 20:");
        for (String name : people.keySet()) {
            if (people.get(name) > 20) {
                System.out.println(name + " → " + people.get(name));
            }
        }
    }
}
```
*Комментарий*: `List` и `Map` — часть ООП, но для задачи достаточно знать синтаксис, как списки и словари в Python.

</details>


---

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


<details><summary><b>
Пример решения
</b></summary>

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

</details>


---

### **5. Отладка: Поиск ошибок**  
**Цель**: Использовать дебаггер для анализа кода из п.4.  
**Задания**:  
1. Поставьте breakpoint в `try/catch` (п.4), проверьте изменение переменных.  

**Ссылки**:  
- [Debugging in IntelliJ (JetBrains)](https://www.jetbrains.com/help/idea/debugging-code.html)  

<details><summary><b>
Пример решения
</b></summary>

- Запустите программу в дебаггере, поставьте breakpoint на `int age = scanner.nextInt();`.  
- При вводе `abc` увидите:  
  - Переменная `e` содержит текст ошибки.  
  - Выполнение переходит в `catch` блок. 

</details>
 

---

### **6. Создание дистрибутива**  
**Цель**: Собрать программу из п.4 в `jar`.  
**Задания**:  
1. Скомпилируйте и соберите в `jar` программу с вводом имени и возраста.  

**Ссылки**:  
- [Building JAR Files (Oracle)](https://docs.oracle.com/javase/tutorial/deployment/jar/build.html)  

<details><summary><b>
Пример решения
</b></summary>

```bash
javac Main.java
jar cfe myapp.jar Main Main.class
java -jar myapp.jar
```

</details>


---

### **7. Maven: Управление зависимостями**  
**Цель**: Подключить внешние библиотеки.  
**Задания**:  
1. Используйте `StringUtils.capitalize()` из `commons-lang3`.  

**Ссылки**:  
- [Maven in 5 Minutes (Apache)](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)  
- https://github.com/gochaorg/blog/tree/master/itdocs/maven



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
- Потоки Reader и Writer https://java-online.ru/java-reader.xhtml
- [Электронная библиотека БГУ / Java. Потоки ввода-вывода. Работа с файлами. Кравчук А ...](https://elib.bsu.by/bitstream/123456789/294799/1/Java.%20%D0%9F%D0%BE%D1%82%D0%BE%D0%BA%D0%B8%20%D0%B2%D0%B2%D0%BE%D0%B4%D0%B0-%D0%B2%D1%8B%D0%B2%D0%BE%D0%B4%D0%B0.%20%D0%A0%D0%B0%D0%B1%D0%BE%D1%82%D0%B0%20%D1%81%20%D1%84%D0%B0%D0%B9%D0%BB%D0%B0%D0%BC%D0%B8.%20%D0%9A%D1%80%D0%B0%D0%B2%D1%87%D1%83%D0%BA%20%D0%90.%D0%A1.%2C%20%D0%9A%D1%80%D0%B0%D0%B2%D1%87%D1%83%D0%BA%20%D0%90.%D0%98.%2C%20%D0%9A%D1%80%D0%B5%D0%BC%D0%B5%D0%BD%D1%8C%20%D0%95.%D0%92_.pdf)

<details><summary><b>
Пример решения
</b></summary>


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

</details>



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

</details>