# Создание дистрибутива

### попытка 1

#### компиляция исходного кода Java

```text
2025-09-13T08:45:09 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ tree -L 1
.
├── pom.xml
├── README.md
├── seLFaq.md
├── src
├── SUGGESTIONS.md
└── target
```

- исходный код находится в src
- структура проекта диктует хранить байт код в каталоге `target`
- параметры компилятора `javac`
```text
Usage: javac <options> <source files>
...
-d <directory>               Specify where to place generated class files
```
- составляю и выполняю команду компиляции
`javac -d "target/classes/pop/lesson04" "src/main/java/pop/lesson04/ScannerInput.java"`

- компиляция выполнена без ошибок
- выходную директорию указал неверно - лишняя вложенность
  - предполагаю, компилятор за исходный путь принимает всю иерархию каталога `java`
```text
2025-09-13T08:51:38 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ tree target/classes/pop/lesson04
target/classes/pop/lesson04
├── pop
│   └── lesson04
│       ├── ScannerInput$1.class
│       ├── ScannerInput$2.class
│       ├── ScannerInput$3.class
│       ├── ScannerInput$4.class
│       └── ScannerInput.class
├── ScannerInput$1.class
├── ScannerInput$2.class
├── ScannerInput$3.class
├── ScannerInput$4.class
└── ScannerInput.class
```

- удаляю некорректные файлы
```text
2025-09-13T08:53:01 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ rm -rf target/classes/pop/lesson04/pop/
2025-09-13T08:55:33 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ tree target/classes/pop/lesson04
target/classes/pop/lesson04
├── ScannerInput$1.class
├── ScannerInput$2.class
├── ScannerInput$3.class
├── ScannerInput$4.class
└── ScannerInput.class
```

- корректирую путь и повторяю компиляцию
```shell
javac -d "target/classes" "src/main/java/pop/lesson04/ScannerInput.java"
```

```text
2025-09-13T08:55:35 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ javac -d "target/classes" "src/main/java/pop/lesson04/ScannerInput.java"
2025-09-13T08:56:50 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ tree -D target/classes/pop/lesson04
target/classes/pop/lesson04
├── [Sep 13 13:56]  ScannerInput$1.class
├── [Sep 13 13:56]  ScannerInput$2.class
├── [Sep 13 13:56]  ScannerInput$3.class
├── [Sep 13 13:56]  ScannerInput$4.class
└── [Sep 13 13:56]  ScannerInput.class
```

#### создание архива сборки приложения / JAR file

- интерфейс программы `jar` напоминает интерфейс `tar`
- учитываю параметры
```text
  -C DIR                     Change to the specified directory and include the
                             following file
  -f, --file=FILE            The archive file name. When omitted, either stdin or
                             stdout is used based on the operation
      --release VERSION      Places all following files in a versioned directory
                             of the jar (i.e. META-INF/versions/VERSION/)
  -v, --verbose              Generate verbose output on standard output
```
- составляю и выполняю команду создания файла jar
`jar --create --file /tmp/scanner_input.jar -C target/classes/pop/lesson04 ScannerInput.class`

```text
2025-09-13T09:11:08 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ ll /tmp/*.jar
-rw-rw-r-- 1 xubuser xubuser 3005 сен 13 14:10 /tmp/scanner_input.jar
```

#### выполнение из файла jar

- выполняю
`java /tmp/scanner_input.jar`

- ошибка
```text
Error: Could not find or load main class scanner_input.jar
Caused by: java.lang.ClassNotFoundException: scanner_input.jar
```

- программа `jar` зависает при попытке прочитать содержимое
```text
2025-09-13T09:19:28 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ jar -t /tmp/scanner_input.jar

```

- создаю jar заново из директории байт-кода
`cd target/classes/pop/lesson04`
- составляю и выполняю команду создания файла jar
`jar --create --file /tmp/scanner_input.jar ScannerInput.class`

```text
2025-09-13T09:31:29 ~/projects/silly_bus/java_basics/popJava/target/classes/pop/lesson04  (lesson_06)₽ ll -t /tmp/scanner_input.jar 
-rw-rw-r-- 1 xubuser xubuser 3005 сен 13 14:30 /tmp/scanner_input.jar
```

- та же проблема
```text
2025-09-13T09:32:08 ~/projects/silly_bus/java_basics/popJava/target/classes/pop/lesson04  (lesson_06)₽ jar -t /tmp/scanner_input.jar 
^C
2025-09-13T09:32:15 ~/projects/silly_bus/java_basics/popJava/target/classes/pop/lesson04  (lesson_06)₽ java /tmp/scanner_input.jar 
Error: Could not find or load main class .tmp.scanner_input.jar
Caused by: java.lang.ClassNotFoundException: /tmp/scanner_input/jar
2025-09-13T09:32:27 ~/projects/silly_bus/java_basics/popJava/target/classes/pop/lesson04  (lesson_06)₽ cd /tmp/
2025-09-13T09:32:33 /tmp ₽ java scanner_input.jar 
Error: Could not find or load main class scanner_input.jar
Caused by: java.lang.ClassNotFoundException: scanner_input.jar
```

- выполинл процедуру создания jar в каталоге бинарников, проблема не в путях
  - гипотеза - проблема в наименовании класса, должно быть `Main`
```text
2025-09-13T09:36:04 ~/projects/silly_bus/java_basics/popJava/target/classes/pop/lesson04  (lesson_06)₽ tar cf scanner-input.jar ScannerInput.class 
2025-09-13T09:36:32 ~/projects/silly_bus/java_basics/popJava/target/classes/pop/lesson04  (lesson_06)₽ ll
total 44
drwxrwxr-x 6 xubuser xubuser  4096 сен 13 13:38  ..
-rw-rw-r-- 1 xubuser xubuser   482 сен 13 13:56 'ScannerInput$1.class'
-rw-rw-r-- 1 xubuser xubuser   524 сен 13 13:56 'ScannerInput$3.class'
-rw-rw-r-- 1 xubuser xubuser   521 сен 13 13:56 'ScannerInput$2.class'
-rw-rw-r-- 1 xubuser xubuser   524 сен 13 13:56 'ScannerInput$4.class'
-rw-rw-r-- 1 xubuser xubuser  5245 сен 13 13:56  ScannerInput.class
drwxrwxr-x 2 xubuser xubuser  4096 сен 13 14:36  .
-rw-rw-r-- 1 xubuser xubuser 10240 сен 13 14:36  scanner-input.jar
2025-09-13T09:36:34 ~/projects/silly_bus/java_basics/popJava/target/classes/pop/lesson04  (lesson_06)₽ java scanner-input.jar
Error: Could not find or load main class scanner-input.jar
Caused by: java.lang.ClassNotFoundException: scanner-input.jar
```

---

### попытка 2

- выполняю
  `java /tmp/scanner_input.jar`

#### компиляция исходного кода Java

```text
2025-09-13T09:41:38 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ tree -L 1 -D
.
├── [Sep 13 08:33]  pom.xml
├── [Sep 13 13:38]  README.md
├── [Sep 13 08:33]  seLFaq.md
├── [Sep 13 08:33]  src
├── [Sep 13 08:33]  SUGGESTIONS.md
└── [Aug 22 08:39]  target
```

```text
2025-09-13T09:43:10 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ ls -l src/main/java/pop/lesson04/*
-rw-rw-r-- 1 xubuser xubuser 7038 сен 13 14:41 src/main/java/pop/lesson04/Main.java
-rw-rw-r-- 1 xubuser xubuser 4299 сен 13 09:37 src/main/java/pop/lesson04/README.md
-rw-rw-r-- 1 xubuser xubuser 7046 сен 13 09:37 src/main/java/pop/lesson04/ScannerInput.java
```

- составляю и выполняю команду компиляции
`javac -d "target/classes/pop/lesson04" "src/main/java/pop/lesson04/Main.java"`
  
```text
2025-09-13T09:45:28 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ javac -d "target/classes" "src/main/java/pop/lesson04/Main.java"
2025-09-13T09:46:00 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ ll target/classes/pop/lesson04
total 32
drwxrwxr-x 3 xubuser xubuser 4096 сен 13 14:45  ..
-rw-rw-r-- 1 xubuser xubuser  500 сен 13 14:45 'Main$4.class'
-rw-rw-r-- 1 xubuser xubuser  500 сен 13 14:45 'Main$3.class'
-rw-rw-r-- 1 xubuser xubuser  497 сен 13 14:45 'Main$2.class'
-rw-rw-r-- 1 xubuser xubuser  458 сен 13 14:45 'Main$1.class'
drwxrwxr-x 2 xubuser xubuser 4096 сен 13 14:45  .
-rw-rw-r-- 1 xubuser xubuser 5197 сен 13 14:45  Main.class
```

- составляю и выполняю команду создания файла jar
  `jar --create --file /tmp/scanner_input.jar -C target/classes/pop/lesson04 Main.class`

```text
2025-09-13T09:47:27 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ ll /tmp/scanner_input.jar 
-rw-rw-r-- 1 xubuser xubuser 2987 сен 13 14:47 /tmp/scanner_input.jar
```

#### выполнение из файла jar
- была ошибка в том, что я не указывал java-машине имя параметра `-jar`
- выполняю `java -jar /tmp/scanner_input.jar`
```text
2025-09-13T09:51:01 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ java -jar /tmp/scanner_input.jar 
no main manifest attribute, in /tmp/scanner_input.jar
```

- не найден файл манифеста, хотя, по доке, вызов `jar cf jar-file input-file(s)` должен был сгенерить манифест
  - > The command will also generate a default manifest file for the JAR archive.

- проверяю содержимое архива
```text
2025-09-13T14:35:33 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ unzip -l /tmp/scanner_input.jar 
Archive:  /tmp/scanner_input.jar
  Length      Date    Time    Name
---------  ---------- -----   ----
        0  2025-09-13 14:47   META-INF/
       55  2025-09-13 14:47   META-INF/MANIFEST.MF
     5197  2025-09-13 14:45   Main.class
---------                     -------
     5252                     3 files
```

- содержимое манифеста
```text
2025-09-13T14:43:01 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ unzip -p /tmp/scanner_input.jar META-INF/MANIFEST.MF | cat
Manifest-Version: 1.0
Created-By: 17.0.15 (Ubuntu)
```

- проблема - не указана точка входа программы
- создаю манифест
```shell
echo "Main-Class: pop.lesson04.ScannerInput" > src/main/java/pop/lesson06/Manifest.txt
```

- повторяю процедуру создания jar

```shell
javac -d "target/classes" "src/main/java/pop/lesson04/ScannerInput.java"
```
```shell
jar --create --file /tmp/scanner_input.jar --manifest src/main/java/pop/lesson06/Manifest.txt -C target/classes/pop/lesson04 ScannerInput.class
```
```shell
java -jar /tmp/scanner_input.jar
```

- ошибка связана с именем класса
```text
2025-09-13T15:04:13 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ java -jar /tmp/scanner_input.jar
Error: Could not find or load main class pop.lesson04.ScannerInput
```

- передаю имя класса в jar с помощью параметра `--main-class`
```shell
jar --create --file /tmp/scanner_input.jar --main-class=ScannerInput target/classes/pop/lesson04/ScannerInput.class
```

```text
2025-09-13T15:15:43 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ jar --create --file /tmp/scanner_input.jar --main-class=ScannerInput target/classes/pop/lesson04/ScannerInput.class
2025-09-13T15:15:50 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ ll /tmp/scanner_input.jar 
-rw-rw-r-- 1 xubuser xubuser 3087 сен 13 20:15 /tmp/scanner_input.jar
2025-09-13T15:16:05 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ unzip -p /tmp/scanner_input.jar META-INF/MANIFEST.MF | cat
Manifest-Version: 1.0
Created-By: 17.0.15 (Ubuntu)
Main-Class: ScannerInput
```

- не найден или не загружен основной класс
```text
2025-09-13T15:17:03 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ java -jar /tmp/scanner_input.jar
Error: Could not find or load main class ScannerInput
Caused by: java.lang.ClassNotFoundException: ScannerInput
```

