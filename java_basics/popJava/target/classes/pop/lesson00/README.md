# **0. Основные понятия, настройка проекта**

## установка Java на Linux

[установка JDK 17](https://docs.oracle.com/en/java/javase/17/install)


## вопросы

### что обозначает номер в начале строки вывода jshell?
```shell
jshell
|  Welcome to JShell -- Version 17.0.15
|  For an introduction type: /help intro

jshell> String s = "123"
s ==> "123"

jshell> String s = "zxcv123"
s ==> "zxcv123"

jshell> s.charAt(2) == 'c'
$3 ==> true

jshell> s.charAt(2)
$4 ==> 'c'

jshell> s.charAt(4)
$5 ==> '1'

jshell> StringBuilder sb = new StringBuilder("asdf123");
sb ==> asdf123

jshell> sb
sb ==> asdf123

jshell> sb.toString()
$8 ==> "asdf123"
```


#### ответ

---

### каталог `JAVA_HOME` содержит символьную ссылку на архив исходников и документации - `src.zip`, но архив по ссылке отсуствует. Как получить оффлайн документацию javadoc для глобальных и локальных внешних библиотек?


#### ответ

> из [документации JDK 17](https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html) The JDK documentation is a separate download
---
