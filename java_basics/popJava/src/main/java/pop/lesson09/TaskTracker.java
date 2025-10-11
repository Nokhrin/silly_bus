package pop.lesson09;


import java.io.*;

/**
 * Консольный трекер задач
 */
public class TaskTracker {
    static BufferedWriter be = new BufferedWriter(new OutputStreamWriter(System.err));

    /**
     * Точка входа
     *
     * @param args имя программы и параметры запуска
     */
    public static void main(String[] args) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out))) {

            TasksList tasksList = TasksList.getInstance();

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
            bw.write("""
                                        
                    >>> Трекер задач <<<
                                        
                    help    справка о командах
                    quit    завершение работы
                                        
                    Введи команду
                    """);
            bw.flush();

            String input;
            while ((input = br.readLine()) != null) {
                input = input.trim();
                if ("".equals(input) || "quit".equalsIgnoreCase(input)) {
                    bw.write("""
                            
                            Завершаю работу Трекера
                            
                            """);
                    return;
                }
                if ("help".equalsIgnoreCase(input)) {
                    bw.write("""
                                                
                            list     напечатать список задач
                            add      создать задачу
                            show     напечатать задачу
                            edit     редактировать задачу
                            delete   удалить задачу
                            
                            write    экспорт списка задач в файл
                            read     импорт списка задач из файла
                                                
                            """);
                } else if ("list".equalsIgnoreCase(input)) {
                    if (tasksList.isEmpty()) {
                        if (tasksList.isEmpty()) {
                            bw.write("""
                                    
                                    Список задач пуст
                                    
                                    """);
                        }
                    } else {
                        bw.write(String.format(
                            """

                            Список задач
                            ============

                            %s
                            
                            ============
                            
                            """,
                                tasksList
                                )
                        );
                    }
                } else if ("add".equalsIgnoreCase(input)) {
                    bw.write("""
                            
                            Создание задачи
                            
                            """);

                    String taskDefinition = "";
                    while (taskDefinition.trim().isEmpty()) {
                        bw.write("Введи постановку задачи\n");
                        bw.flush();
                        taskDefinition = br.readLine();
                        if (taskDefinition.trim().isEmpty()) {
                            be.write("Значение не может быть пустым\n\n");
                            be.flush();
                        }
                    }

                    String taskOwner = "";
                    while (taskOwner.trim().isEmpty()) {
                        bw.write("Введи имя владельца задачи\n");
                        bw.flush();
                        taskOwner = br.readLine();
                        if (taskOwner.trim().isEmpty()) {
                            be.write("Значение не может быть пустым\n\n");
                            be.flush();
                        }
                    }

                    Task task = new Task(taskDefinition, taskOwner);
                    tasksList.addTask(task);
                    bw.write(String.format("""
                            
                            Создана задача
                            %s
                            
                            """, task));

                } else if ("delete".equalsIgnoreCase(input)) {
                    bw.write("""
                            
                            Удалить задачу
                            
                            Введи id задачи
                            """);

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
                        bw.write(String.format("""
                                Задача id=%s
                                успешно удалена
                                
                                """, taskId));
                    } else {
                        bw.write("""
                                Удаление задачи не удалось
                                """);
                    }

                } else if ("write".equalsIgnoreCase(input)) {
                    if (tasksList.isEmpty()) {
                        bw.write("""
                                Список задач пуст
                                                            
                                """);
                        bw.flush();
                    } else {

                        bw.write("""
                            
                            Записать задачи в файл
                            
                            """);
                        bw.flush();

                        String fileName = "";
                        while (fileName.trim().isEmpty()) {
                            bw.write("Введи имя файла\n");
                            bw.flush();
                            fileName = br.readLine();
                            if (fileName.trim().isEmpty()) {
                                be.write("Значение не может быть пустым\n\n");
                                be.flush();
                                continue;
                            }
                            tasksList.toStore(fileName);

                            bw.write("Задачи записаны в файл " + fileName + "\n\n");
                        }
                    }

                } else if ("read".equalsIgnoreCase(input)) {
                    bw.write("""
                            
                            Добавить задачи из файла
                            
                            """);
                    bw.flush();

                    String fileName = "";
                    while (fileName.trim().isEmpty()) {
                        bw.write("Введи имя файла\n");
                        bw.flush();
                        fileName = br.readLine();
                        if (fileName.trim().isEmpty()) {
                            be.write("Значение не может быть пустым\n\n");
                            be.flush();
                            continue;
                        }
                        tasksList.fromStore(fileName);

                        bw.write("Добавлены задачи из файла " + fileName);
                        bw.newLine();
                    }

                } else if ("edit".equalsIgnoreCase(input)) {
                    if (tasksList.isEmpty()) {
                        bw.write("""
                                Список задач пуст
                                                            
                                """);
                        bw.flush();
                        continue;
                    }

                    bw.write("""
                            
                            Редактировать задачу
                            
                            """);

                    String taskId = "";
                    while (taskId.trim().isEmpty()) {
                        bw.write("Введи id задачи\n");
                        bw.flush();
                        taskId = br.readLine();
                        if (taskId.trim().isEmpty()) {
                            be.write("Значение не может быть пустым\n\n");
                            be.flush();
                        }
                    }
                    Task taskToEdit = tasksList.getTaskById(taskId);
                    if (taskToEdit == null) {
                        throw new IllegalArgumentException("Задача с id=" + taskId + " не существует");
                    }
                    bw.write(String.format("""
                            Задача
                            
                            %s
                            
                            Доступны для редактирования поля: name, owner, done
                                                    
                            Введи имя поля для редактирования
                            """,
                            taskToEdit));
                    bw.flush();
                    String fieldName = br.readLine();

                    if (fieldName.trim().equalsIgnoreCase("name")) {
                        bw.write("Введи новое имя задачи: ");
                        bw.flush();
                        String value = br.readLine();
                        if (value.trim().equals("")) {
                            throw new IOException("В качестве значения получена пустая строка");
                        }
                        taskToEdit.setDefinition(value);
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
                                
                                """);
                    } else {

                        bw.write("""
                                
                                Напечатать задачу
                                
                                """);
                        bw.flush();

                        String taskId = "";
                        while (taskId.trim().isEmpty()) {
                            bw.write("Введи id задачи\n");
                            bw.flush();
                            taskId = br.readLine();
                            if (taskId.trim().isEmpty()) {
                                be.write("Значение не может быть пустым\n\n");
                                be.flush();
                                continue;
                            }
                            Task task = tasksList.getTaskById(taskId);
                            if (task == null) {
                                be.write("Задача с id=" + taskId + " не существует\n\n");
                                be.flush();
                                continue;
                            }
                            Task taskToPrint = tasksList.getTaskById(taskId);

                            bw.write(String.format("""
                                
                                Задача
                                %s
                                
                                """, taskToPrint));
                        }

                    }
                    bw.flush();

                } else {
                    be.write("'" + input + "' не является командой\n\n");
                    be.flush();
                }

                bw.write("Введи команду\n");
                bw.flush();

            }

        } catch (IOException e) {
            be.write("Ошибка ввода / вывода " + e);
            be.flush();
        }
    }
}