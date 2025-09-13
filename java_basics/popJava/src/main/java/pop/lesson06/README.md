### **6. Создание дистрибутива**
**Цель**: Собрать программу из п.4 в `jar`.  
**Задания**:
1. Скомпилируйте и соберите в `jar` программу с вводом имени и возраста.

**Ссылки**:
- [Building JAR Files (Oracle)](https://docs.oracle.com/javase/tutorial/deployment/jar/build.html)

**Пример решения**:
```bash
javac Main.java
jar cfe myapp.jar Main Main.class
java -jar myapp.jar
```