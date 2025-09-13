## 5. Отладка: Поиск ошибок

### точки останова

```java
public class ScannerInput {
    private static int getAge(Scanner scanner, PrintStream ps) {
        while (true) {
            try {
                ps.print("Введи возраст, нажми Enter: ");
                // breakpoint_1
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException errInt) {
                ps.println("Введенное значение не является целым числом");
                // breakpoint_2
            }
        }
    }
}
```

### запуск в режиме отладки
- Run -> Debug -> ScannerInput.java
- выполнение останавливается на первой блокирующей операции - запросе ввода

```text
Программа создает словарь имя->возраст
и позволяет добавить записи в словарь

Чтобы начать добавление записи, выполни /add
Чтобы посмотреть существующие записи, выполни /show
Для вывода всех поддерживаемых комманд, выполни /help

Введи команду, нажми Enter (/help для отображения доступных команд): 
```

- 

### значения в `breakpoint_1`
- объект сканера / InputStream
- объект потока вывода / PrintStream

### значения в `breakpoint_2`
- объект исключения

