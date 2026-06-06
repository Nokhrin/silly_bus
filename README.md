# Учебный репозиторий с модульной структурой

| Модуль           | Назначение                                                                              | Ключевые концепции                                         |
|------------------|-----------------------------------------------------------------------------------------|------------------------------------------------------------|
| `task-tracker/`  | Консольный трекер задач (поэтапное развитие от базового синтаксиса до финальной версии) | Java Core, Collections, File I/O, Maven, SLF4J, JUnit 5    |
| `fin-calc/`      | Система управления банковскими счетами                                                  | Command Pattern, State Machine, UML, TestNG, JaCoCo        |
| `calc-parser/`   | Рекурсивный парсер арифметических выражений                                             | Recursive Descent, AST, CLI, юнит-тестирование             |
| `lambda-parser/` | Библиотека комбинаторных парсеров                                                       | Функциональные комбинаторы, Generics, Java Records, TestNG |
| `asm-projects/`  | Основы ассемблера x86-64 и системные вызовы                                             | NASM, LD, GDB, Syscalls, ELF-структуры                     |


# Настройка окружения


## Установка пакета разработки Java

> установка определенной версии, выбор используемой по умолчанию

### Путь к исполняемому файлу в PATH
```shell
which java
### Выбор версии по умолчанию
update-alternatives --config java
update-alternatives --config javac

### Путь к домашней директории JDK - указывать только одну версию, не список
echo $JAVA_HOME

#### Проверка
java --version
```

## Установка определенной версии Java в openSUSE Tumbleweed
```shell
### Список доступных неустановленных RPM-пакетов Java
zypper search --not-installed-only java*openjdk*devel
### Список установленных RPM-пакетов Java
zypper search --installed-only java*openjdk*devel
### Пример установки: JDK 21
zypper install java-21-openjdk-devel
### Найти jdk 21 для указания в JAVA_HOME
```shell
find / -type d -iname "*jdk*" -not -iname "*runtime*" 2>/dev/null | grep -i "21" | while read d; do [[ -f "$d/bin/java" ]] && echo "$d"; done
```

Ожидаемый вывод:
```
/home/.devtools/java/jdk-21.0.5
/usr/lib/jvm/axiomjdk-java21-pro-amd64
```

### Настроить `JAVA_HOME` и `PATH`
> пример для текущей сессии
> для установки по умолчанию указать эти команды в `.bashrc`
```shell
export JAVA_HOME=/usr/lib/jvm/axiomjdk-java21-pro-amd64
export PATH=$JAVA_HOME/bin:$PATH
```

### Настройки компилятора для отладки

```
Логирование интерпретации LambdaForm
-Djava.lang.invoke.MethodHandle.TRACE_INTERPRETER=true

Трассировка связывания вызовов invokedynamic
-Djava.lang.invoke.MethodHandle.TRACE_METHOD_LINKAGE=true

Логирование разрешения LambdaForm в MemberName
-Djava.lang.invoke.MethodHandle.TRACE_RESOLVE=true

Дамп сгенерированных байткод-классов в указанную директорию
-Djdk.invoke.MethodHandle.dumpMethodHandleInternals=/tmp
```

### Линтер + Форматтер
```shell
# проверка
mvn spotless:check
# исправление
mvn spotless:apply
# для конкретного модуля
mvn spotless:check -pl task-tracker,asm-projects
# проверки определенного типа файлов
mvn spotless:check -DspotlessFiles=.*\.md
```

---


## [Наставник - Георгий Камнев](https://github.com/gochaorg)

