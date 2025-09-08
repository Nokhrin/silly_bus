### **0. Основные понятия, настройка проекта**


## вопросы

- что обозначает номер в начале строки вывода jshell?
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