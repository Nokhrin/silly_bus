package pop.lesson09;


import java.io.*;

/**
 * Консольный трекер задач
 * ---
 * Трекер является системой управления задачами
 * ---
 * Пользовательский интерфейс
 * - аргументы командной строки при запуске приложения
 * - интерактивный текстовый режим - после запуска
 * ---
 * WIP - ОБРАБОТКА КОМАНД
 * -общие
 * quit
 * help
 * version
 * <p>
 * -список задач
 * list список задач
 * add добавить задачу
 * delete удалить задачу
 * write экспорт в файл
 * read импорт из файла
 * <p>
 * -задача
 * create создать задачу
 * edit редактировать задачу
 * show напечатать задачу
 * <p>
 * -command line args
 * "--read filepath", "-r filepath" импорт задач из файла
 * "--help", "-h", руководство пользователя
 * "--about" информация о приложении
 */
public class TaskTracker {

    /**
     * Точка входа
     *
     * @param args имя программы и параметры запуска
     */
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
             BufferedWriter be = new BufferedWriter(new OutputStreamWriter(System.err))) {

            // пустой экземпляр списка задач
            TasksList tasksList = new TasksList();

            // параметры командной строки
            if (args.length != 0) {
                for (String arg : args) {
                    // разбор ключа загрузки задач из файла
                    if ("--about".equals(arg)) {
                        bw.write("""
                            Трекер задач
                            версия 0.1
                            """);
                        bw.newLine();
                        bw.flush();
                        return;

                    } else if ("--help".equals(arg) || "-h".equals(arg)) {
                        bw.write("""
                            Запуск: java -jar TaskTracker.jar [опции]
                            --read=путь/к/файлу   Загрузить задачи из файла
                            --about   Сведения о программе
                            --help   Помощь
                            
                            Пример
                            java -jar TaskTracker.jar --read=bkp/tasks.csv
                            java -jar TaskTracker.jar --about
                            """);
                        bw.flush();
                        return;

                    } else if (arg.startsWith("--read")) {
                        String[] parts = arg.split("=", 2);
                        if (parts.length > 1) {
                            String fileName = parts[1];
                            if (!fileName.equals("")) {
                                bw.write("Получен путь к файлу задач: " + fileName + '\n');
                                bw.flush();
                                tasksList.fromStore(fileName);
                                break;
                            }
                        }
                        throw new IllegalArgumentException("Некорректное значение параметра read");
                    }
                }
            }


            // старт меню
            bw.write(">>> Трекер задач <<<");
            bw.newLine();
            bw.flush();

            String input;

            bw.write("Введите команду - help для вывода справки о командах, quit или пустую строку для завершения работы");
            bw.newLine();
            bw.flush();  // строка будет выведена только при использовании flush
            while ((input = br.readLine()) != null) {
                // чтение команды
                if ("".equals(input.trim()) || "quit".equalsIgnoreCase(input.trim())) {
                    bw.write("Завершаю работу Трекера");
                    bw.newLine();
                    break;
                }
                // NOTE: умышленно не применил switch, чтобы использовать equalsIgnoreCase
                //  если правильно понимаю, switch выполняет тот же if-else + equals 
                if ("help".equalsIgnoreCase(input)) {
                    bw.write("""
                    -список задач
                    list     список задач
                    add      добавить задачу
                    delete   удалить задачу
                    write    экспорт в файл
                    read     импорт из файла
                    
                    -задача
                    create   создать задачу
                    edit     редактировать задачу
                    show     напечатать задачу
                    
                    """);
                    bw.newLine();
                } else if ("list".equalsIgnoreCase(input)) {
                    if (tasksList.isEmpty()) {
                        if (tasksList.isEmpty()) {
                            bw.write("""
                            Список задач пуст
                            
                            Введи команду
                            """);
                            bw.flush();
                            continue;
                        }

                    } else {
                        bw.write(tasksList.toString());
                    }
                    bw.newLine();
                } else if ("add".equalsIgnoreCase(input)) {
                    Task task = new Task();
                    bw.write("Создана задача " + task + "\n\n");
                } else if ("delete".equalsIgnoreCase(input)) {
                    bw.write("""
                        Удалить задачу
                        Введи id задачи: """);

                    bw.newLine();
                    bw.newLine();
                    bw.flush();

                    String taskId = br.readLine();
                    if (taskId.trim().equals("")) {
                        throw new IOException("В качестве id задачи получена пустая строка");
                    }
                    Task taskToDelete = tasksList.getTaskById(taskId);
                    if (taskToDelete == null) {
                        throw new IllegalArgumentException("Задача с id=" + taskId + " не существует");
                    }

                    if (tasksList.delTask(taskToDelete)) {
                        bw.write("""
                                Задача успешно удалена
                                """);
                    } else {
                        bw.write("""
                                Удаление задачи не удалось
                                """);
                    }

                } else if ("write".equalsIgnoreCase(input)) {
                    if (tasksList.isEmpty()) {
                        bw.write("""
                            Список задач пуст
                            
                            Введи команду
                            """);
                        bw.flush();
                        continue;
                    }

                    bw.write("""
                            Записать задачи в файл
                            Введи имя файла: """);
                    bw.flush();

                    String fileOut = br.readLine();
                    if (fileOut.trim().equals("")) {
                        throw new IOException("В качестве имени файла получена пустая строка");
                    }

                    tasksList.toStore(fileOut);

                    bw.write("Задачи записаны в файл " + fileOut);
                    bw.newLine();
                    bw.newLine();

                } else if ("read".equalsIgnoreCase(input)) {
                    bw.write("""
                            Добавить задачи из файла
                            Введи имя файла: """);
                    bw.flush();

                    String fileIn = br.readLine();
                    if (fileIn.trim().equals("")) {
                        throw new IOException("В качестве имени файла получена пустая строка");
                    }

                    tasksList.fromStore(fileIn);

                    bw.write("Добавлены задачи из файла " + fileIn);
                    bw.newLine();

                } else if ("create".equalsIgnoreCase(input)) {
                    bw.write("""
                            Создать задачу
                            Введи имя задачи: """);
                    bw.flush();

                    String taskName = br.readLine();
                    if (taskName.trim().equals("")) {
                        throw new IOException("В качестве имени задачи получена пустая строка");
                    }
                    bw.newLine();

                    bw.write("Введи владельца задачи: ");
                    String taskOwner = br.readLine();
                    bw.newLine();
                    bw.flush();

                    if (taskOwner.trim().equals("")) {
                        throw new IOException("В качестве владельца задачи получена пустая строка");
                    }

                    Task task = new Task(taskName, taskOwner);

                } else if ("edit".equalsIgnoreCase(input)) {
                    if (tasksList.isEmpty()) {
                        bw.write("""
                            Список задач пуст
                            
                            Введи команду
                            """);
                        bw.flush();
                        continue;
                    }

                    bw.write("""
                        Редактировать задачу
                        Введи id задачи: """);

                    String taskId = br.readLine();
                    if (taskId.trim().equals("")) {
                        throw new IOException("В качестве id задачи получена пустая строка");
                    }
                    Task taskToEdit = tasksList.getTaskById(taskId);
                    if (taskToEdit == null) {
                        throw new IllegalArgumentException("Задача с id=" + taskId + " не существует");
                    }
                    bw.write("""
                        Задача
                        """ +
                        taskToEdit.toString() +
                        """
                        Доступны для редактирования поля: name, owner, done
                        
                        Введи имя поля для редактирования: """);
                    bw.flush();
                    String fieldName = br.readLine();

                    if (fieldName.trim().equalsIgnoreCase("name")) {
                        bw.write("Введи новое имя задачи: ");
                        bw.flush();
                        String value = br.readLine();
                        if (value.trim().equals("")) {
                            throw new IOException("В качестве значения получена пустая строка");
                        }
                        taskToEdit.setName(value);
                    } else if (fieldName.trim().equalsIgnoreCase("owner")) {
                        bw.write("Введи нового владельца задачи: ");
                        bw.flush();
                        String value = br.readLine();
                        if (value.trim().equals("")) {
                            throw new IOException("В качестве значения получена пустая строка");
                        }
                        taskToEdit.setOwner(value);
                    } else if (fieldName.trim().equalsIgnoreCase("done")) {
                        bw.write("Введи y для изменения статуса, введи иное значение для отмены: ");
                        bw.flush();
                        String value = br.readLine();
                        if (!value.trim().equals("y")) {
                            bw.write("Переключение статуса отменено");
                            bw.newLine();
                            bw.flush();
                            break;
                        }
                        if (taskToEdit.isDone()) {
                            taskToEdit.setDone(false);
                        } else {
                            taskToEdit.setDone(true);
                        }
                    } else {
                        throw new IllegalArgumentException("Получено значение, которое не является именем поля");
                    }

                    bw.flush();

                } else if ("show".equalsIgnoreCase(input)) {
                    if (tasksList.isEmpty()) {
                        bw.write("""
                            Список задач пуст
                            
                            Введи команду
                            """);
                        bw.flush();
                        continue;
                    }

                    bw.write("""
                        Напечатать задачу
                        Введи id задачи: """);
                    bw.flush();

                    String taskId = br.readLine();
                    if (taskId.trim().equals("")) {
                        throw new IOException("В качестве имени задачи получена пустая строка");
                    }

                    Task task = tasksList.getTaskById(taskId);
                    if (task == null) {
                        throw new IllegalArgumentException("Задача с id=" + taskId + " не существует");
                    }


                    Task taskToPrint = tasksList.getTasks().get(Integer.parseInt(taskId));

                    bw.write("Задача");
                    bw.newLine();
                    bw.write(taskToPrint.toString());
                    bw.flush();
                }

                else {
                    bw.write("'" + input + "' не является командой. Повтори ввод");
                    bw.newLine();
                }

                bw.flush();

            }

        } catch (IOException e) {
            System.err.println("Ошибка ввода / вывода " + e);
            System.err.flush();
        }
    }
}