# Настройка окружения

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

