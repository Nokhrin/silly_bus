## Сборка


настроить JDK 21

```shell
# Установите переменную окружения
export JAVA_HOME=/usr/lib/jvm/axiomjdk-java21-pro-amd64

# Добавьте в PATH
export PATH=$JAVA_HOME/bin:$PATH

# Проверьте версию
java -version
javac -version
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

### команды из файла
```shell
java -jar target/fin-calc-1.0.0.jar --file target/commands.txt
```

пример вывода
```
Управление банковскими счетами. Введите команду (или 'exit' для выхода):
> ok Успешно открыт счет f24fae92-b019-4f40-9cd1-b82d97f383b6 [состояние системы было изменено]
ok В системе существуют счета: f24fae92-b019-4f40-9cd1-b82d97f383b6 [состояние системы не было изменено]
> > ok Успешно открыт счет bc47037a-f4b6-42f4-bfc0-64b7a1dc9435 [состояние системы было изменено]
> ok В системе существуют счета: bc47037a-f4b6-42f4-bfc0-64b7a1dc9435 f24fae92-b019-4f40-9cd1-b82d97f383b6 [состояние системы не было изменено]
> Завершение работы
```

### команды из pipe
```shell
cat target/commands.txt | java -jar target/fin-calc-1.0.0.jar
```

пример вывода
```
Управление банковскими счетами. Введите команду (или 'exit' для выхода):
> ok Успешно открыт счет f24fae92-b019-4f40-9cd1-b82d97f383b6 [состояние системы было изменено]
ok В системе существуют счета: f24fae92-b019-4f40-9cd1-b82d97f383b6 [состояние системы не было изменено]
> > ok Успешно открыт счет bc47037a-f4b6-42f4-bfc0-64b7a1dc9435 [состояние системы было изменено]
> ok В системе существуют счета: bc47037a-f4b6-42f4-bfc0-64b7a1dc9435 f24fae92-b019-4f40-9cd1-b82d97f383b6 [состояние системы не было изменено]
> Завершение работы
```