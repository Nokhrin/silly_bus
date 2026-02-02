## Установка

клонировать

перейти в директорию проекта

```shell
cd ~/projects/silly_bus/oopJava/assignments/fin-calc
```

собрать jar
```shell
mvn clean package -Pproduction
```

проверить формирование jar
```shell
ls target/fin*
```
> target/fin-calc-1.0.0.jar

создать файл `target/commands.txt` с командами
```shell
open
list
exit
```

## Запуск

команды из консоли
```shell
java -jar target/fin-calc-1.0.0.jar
```

команды из файла
```shell
java -jar target/fin-calc-1.0.0.jar --file target/commands.txt
```

команды из pipe
```shell
cat target/commands.txt | java -jar target/fin-calc-1.0.0.jar
```
