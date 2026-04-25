# Настройка окружения


# Установка пакета разработки Java

> установка определенной версии, выбор используемой по умолчанию

### Путь к исполняемому файлу в PATH
which java

### Выбор версии по умолчанию
update-alternatives --config java
update-alternatives --config javac

### Путь к домашней директории JDK - указывать только одну версию, не список
echo $JAVA_HOME

#### Проверка
java --version


## Установка Java в openSUSE Tumbleweed
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

