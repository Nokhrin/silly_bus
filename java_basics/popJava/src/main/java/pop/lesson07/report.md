# Управление зависимостями

## Maven

### установка / проверка
IDE может содержать Maven как плагин
[пример - IDEA](https://www.jetbrains.com/help/idea/maven-support.html)

при этом в системе Maven может отсутствовать

mvn --version
```text

Command 'mvn' not found, but can be installed with:

sudo apt install maven
```

проект будет работать, доступны версии Maven, которые встроены в IDE
можно считать это ограничением


- [установка Maven из бинарного архива](https://maven.apache.org/download.cgi), здесь доступны:
  - архив актуального релиза, контрольная сумма, подпись контрольной суммы
  - pgp ключи разработчика
  - процедура проверки подписи архива релиза
  - ссылка на инструкцию по установке


- загрузить и импортировать ключи разработчиков Apache
curl -o /tmp/apache_keys https://downloads.apache.org/maven/KEYS
gpg --import /tmp/apache_keys

- проверить подпись дистрибутива
gpg --verify apache-maven-3.9.11-bin.tar.gz.asc apache-maven-3.9.11-bin.tar.gz
```
gpg: Signature made Сб 12 июл 2025 23:32:55 +05
gpg:                using RSA key 84789D24DF77A32433CE1F079EB80E92EB2135B1
gpg:                issuer "sjaranowski@apache.org"
gpg: Good signature from "Slawomir Jaranowski <sjaranowski@apache.org>" [unknown]
gpg:                 aka "Slawomir Jaranowski <s.jaranowski@gmail.com>" [unknown]
gpg: WARNING: This key is not certified with a trusted signature!
gpg:          There is no indication that the signature belongs to the owner.
Primary key fingerprint: 8478 9D24 DF77 A324 33CE  1F07 9EB8 0E92 EB21 35B1
```

- проверить контрольную сумму
{ cat apache-maven-3.9.11-bin.tar.gz.sha512 | tr '\n' ' ' ; echo " *apache-maven-3.9.11-bin.tar.gz"; } | sha512sum -c
```
apache-maven-3.9.11-bin.tar.gz: OK
```

### установка

<details>
  <summary>шаги и результаты</summary>

- создать каталог для Maven 
mkdir -p ~/.local/apache-maven
- распаковать
tar xzvf apache-maven-3.9.11-bin.tar.gz -C ~/.local/apache-maven
- добавить bin в PATH - изменить `.bashrc`
vim ~/.bashrc

```text
# Maven
export M2_HOME=/home/xubuser/.local/apache-maven/apache-maven-3.9.11/
export M2=$M2_HOME/bin
export PATH=$M2:$PATH
```

source ~/.bashrc

- проверить
mvn --version
```
Apache Maven 3.9.11 (3e54c93a704957b63ee3494413a2b544fd3d825b)
Maven home: /home/xubuser/.local/apache-maven/apache-maven-3.9.11
Java version: 17.0.15, vendor: Ubuntu, runtime: /usr/lib/jvm/java-17-openjdk-amd64
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "5.4.0-216-generic", arch: "amd64", family: "unix"
```
</details>


### создание проекта
- в моем случае проект Maven создал плагин Maven для IDEA сразу после создания проекта в среде разработки

#### структура проекта Maven
- 
- на данном этапе для меня важно понять директории
  - `src/main/java` - исходный код моих классов
  - `src/test/java` - исходный код моих юнит-тестов


<details>
  <summary>Каталог файлов исходного кода</summary>

```text
2025-09-14T05:30:01 ~/projects/silly_bus/java_basics/popJava  (lesson_07)₽ tree -L 5 src/
src/
├── main
│   ├── java
│   │   └── pop
│   │       ├── lesson00
│   │       │   ├── reading_material.md
│   │       │   └── README.md
│   │       ├── lesson01
│   │       │   ├── README.md
│   │       │   ├── StringBasics.java
│   │       │   └── StringReplace.java
│   │       ├── lesson02
│   │       │   ├── MathMain.java
│   │       │   └── README.md
│   │       ├── lesson03
│   │       │   ├── ArrayOps.java
│   │       │   ├── CollectionsBasics.java
│   │       │   └── README.md
│   │       ├── lesson04
│   │       │   ├── README.md
│   │       │   └── ScannerInput.java
│   │       ├── lesson07
│   │       │   ├── README.md
│   │       │   └── report.md
│   │       └── lesson08
│   │           └── README.md
│   └── resources
└── test
    └── java
        └── pop
            ├── lesson01
            │   ├── StringBasicsTest.java
            │   └── StringReplaceTest.java
            ├── lesson02
            │   └── MathMainTest.java
            ├── lesson03
            │   ├── ArrayOpsTest.java
            │   └── CollectionsBasicsTest.java
            └── lesson04
                └── ScannerInputTest.java

```

</details>


### работа с зависимостями
- зависимость представлена `артефактом` / `artifact`
- `артефакт` - это подключаемая Java-библиотека
- центр управления зависимостями - `pom.xml`
  - править `pom.xml`, без сторонних инструментов
- [актуальная дока о maven central](https://maven.apache.org/repository/index.html)

#### добавление артефакта
- найти артефакт в центральном репозитории
  - [maven central](https://mvnrepository.com/repos/central)
- выбрать версию
- на вкладке `Maven` скопировать сгенерированный код
- записать скопированный код в тег зависимостей `pom.xml`

```xml
<!-- pom.xml -->
<!-- ... -->
    <dependencies>
        <!-- ... -->
        <!-- https://mvnrepository.com/artifact/org.testng/testng -->
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>7.11.0</version>
            <scope>test</scope>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.apache.commons/commons-lang3 -->
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.18.0</version>
        </dependency>
        <!-- ... -->
    </dependencies>
<!-- ... -->
```

- выполнить сборку
```shell
mvn package
```

- сборка выполнена успешно

<details>
  <summary>лог сборки</summary>

```text
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------< org.example:popJava >-------------------------
[INFO] Building popJava 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-resources-plugin/3.3.1/maven-resources-plugin-3.3.1.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/plugins/maven-resources-plugin/3.3.1/maven-resources-plugin-3.3.1.pom (8.2 kB at 4.5 kB/s)
...
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ popJava ---
Downloading from central: https://repo.maven.apache.org/maven2/org/codehaus/plexus/plexus-interpolation/1.26/plexus-interpolation-1.26.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/codehaus/plexus/plexus-interpolation/1.26/plexus-interpolation-1.26.pom (2.7 kB at 9.7 kB/s)
...
[INFO] Copying 0 resource from src/main/resources to target/classes
[INFO] 
[INFO] --- compiler:3.13.0:compile (default-compile) @ popJava ---
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/shared/maven-shared-utils/3.4.2/maven-shared-utils-3.4.2.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/shared/maven-shared-utils/3.4.2/maven-shared-utils-3.4.2.pom (5.9 kB at 19 kB/s)
...
[INFO] Recompiling the module because of changed source code.
[INFO] Compiling 6 source files with javac [debug target 17] to target/classes
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ popJava ---
[INFO] skip non existing resourceDirectory /home/xubuser/projects/silly_bus/java_basics/popJava/src/test/resources
[INFO] 
[INFO] --- compiler:3.13.0:testCompile (default-testCompile) @ popJava ---
[INFO] Recompiling the module because of changed dependency.
[INFO] Compiling 6 source files with javac [debug target 17] to target/test-classes
[INFO] 
[INFO] --- surefire:3.2.5:test (default-test) @ popJava ---
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/surefire/maven-surefire-common/3.2.5/maven-surefire-common-3.2.5.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/surefire/maven-surefire-common/3.2.5/maven-surefire-common-3.2.5.pom (6.2 kB at 10 kB/s)
...
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running TestSuite
SLF4J(W): No SLF4J providers were found.
SLF4J(W): Defaulting to no-operation (NOP) logger implementation
SLF4J(W): See https://www.slf4j.org/codes.html#noProviders for further details.
Стартовал юнит-тесты
Sep 14, 2025 11:05:09 AM pop.lesson04.ScannerInputTest testFormatLinkedHashSet
INFO: ожидание: /cmd1
/cmd2
/cmd3
Sep 14, 2025 11:05:09 AM pop.lesson04.ScannerInputTest testFormatLinkedHashSet
INFO: реальность: /cmd1
/cmd2
/cmd3

...

Выполнил юнит-тесты
[INFO] Tests run: 46, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.761 s -- in TestSuite
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 46, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- jar:3.4.1:jar (default-jar) @ popJava ---
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/shared/file-management/3.1.0/file-management-3.1.0.pom
Downloaded from central: https://repo.maven.apache.org/maven2/org/apache/maven/shared/file-management/3.1.0/file-management-3.1.0.pom (4.5 kB at 11 kB/s)
Downloading from central: https://repo.maven.apache.org/maven2/org/apache/maven/shared/maven-shared-components/36/maven-shared-components-36.pom
...
[INFO] Building jar: /home/xubuser/projects/silly_bus/java_basics/popJava/target/popJava-1.0-SNAPSHOT.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  55.968 s
[INFO] Finished at: 2025-09-14T11:05:21+05:00
[INFO] ------------------------------------------------------------------------
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  55.968 s
```

</details>

- в IDEA после добавления - варианты
  - появится иконка refresh в области кода
  - можно нажать syncronize на вкладке maven
  - можно запустить maven compile - скачает, а потом 1/2



## Рабочий процесс Maven

1. **Инициализация** проекта
   - создать проект Maven 
   - определить метаданные проекта и зависимостей в `pom.xml`
2. Вызов процедуры **сборки**
   - выполнить `mvn install` или `mvn package`
3. **Проверка** зависимостей
   - Maven проверяет наличие зависимостей в локальном репозитории
   - При наличии исходников в локальном репозитории Maven установит зависимости
4. Если Maven не найдет зависимости в локальном репозитории, проверит наличие зависимостей в Центральном репозитории
   - если найдет, загрузит исходники,
   - выполнит установку,
   - добавит исходники в локальный репозиторий
5. После успешного разрешения зависимостей Maven выполнит **компиляцию** проекта
6. Упаковка
  - Maven пакует проект в исполняемый файл JVM (JAR/WAR/...)
