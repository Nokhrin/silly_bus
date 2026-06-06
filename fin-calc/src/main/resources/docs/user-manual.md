## Сборка


настроить JDK 21


пример настройки
```shell
# Установите переменную окружения
export JAVA_HOME=/usr/lib/jvm/axiomjdk-java21-pro-amd64

# Добавьте в PATH
export PATH=$JAVA_HOME/bin:$PATH
```

```shell
# Проверьте версию
java -version

# openjdk version "21" 2023-09-19 LTS
# OpenJDK Runtime Environment (build 21+37-LTS)
# OpenJDK 64-Bit Server VM (build 21+37-LTS, mixed mode, sharing)

javac -version

# javac 21

```

клонировать репозиторий

перейти в директорию проекта

```shell
cd ~/projects/silly_bus/oopJava/assignments/fin-calc
```

собрать jar
```shell
mvn clean package -Pproduction
```

создать файл `target/commands.txt` с командами

пример ввода
```text
open list

open  \topen
list
exit
```

создание файла с командами
```shell
printf 'open list\n\nopen \topen\nlist' > target/commands.txt
```


## Запуск

### команды из консоли
```shell
java -jar target/fin-calc-1.0.0.jar
```


---

### команды из файла
```shell
java -jar target/fin-calc-1.0.0.jar --file target/commands.txt
```

### команды из pipe
```shell
cat target/commands.txt | java -jar target/fin-calc-1.0.0.jar
```

пример вывода при чтении из файла и pipe
```
Управление банковскими счетами. Введите команду (или 'exit' для выхода):
ok Успешно открыт счет 10d95e68-7f51-4d7a-801b-9d7d2d4e46c6 [состояние системы было изменено]
ok В системе существуют счета: 10d95e68-7f51-4d7a-801b-9d7d2d4e46c6 [состояние системы не было изменено]
ok Успешно открыт счет d15c2aa5-3cf2-41de-8e79-511c258d097d [состояние системы было изменено]
ok Успешно открыт счет 9d54c2b0-0dff-427c-8c52-036a96299f98 [состояние системы было изменено]
ok В системе существуют счета: d15c2aa5-3cf2-41de-8e79-511c258d097d 10d95e68-7f51-4d7a-801b-9d7d2d4e46c6 9d54c2b0-0dff-427c-8c52-036a96299f98 [состояние системы не было изменено]
```