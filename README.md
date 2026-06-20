# Учебный репозиторий с модульной структурой

## Модули проекта

- [algebraic-interpreter](mylang-interpreter/README.md) - калькулятор школьной алгебры с поддержкой переменных, выводом типов в runtime и грамматикой ANTLR4
- [parser-combinator](./parser-combinator/README.md) - библиотека парсер-комбинаторов для построения рекурсивных спусковых парсеров
- [task-tracker](./task-tracker/README.md) - система управления задачами
- [asm-projects](./asm-projects/README.md) - проекты на ассемблере

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
>
> ```shell
> export JAVA_HOME=/usr/lib/jvm/axiomjdk-java21-pro-amd64
> export PATH=$JAVA_HOME/bin:$PATH
> ```

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

# Инструменты

## Диаграммы drawio

```shell
# Стартовать
docker run -it --rm --name="draw" -p 8080:8080 -p 8443:8443 jgraph/drawio
# http://localhost:8080/
# Остановить
docker stop draw
```

---

# Благодарность

[Наставник - Георгий Камнев](https://github.com/gochaorg)

