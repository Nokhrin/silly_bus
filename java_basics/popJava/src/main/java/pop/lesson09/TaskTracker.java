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
                                bw.write(String.format("""
                                        Получено имя файла задач
                                        '%s'
                                        
                                        """, fileName));

                                try {
                                    tasksList.fromStore(fileName);
                                } catch (FileNotFoundException e) {
                                    bw.write(String.format("""
                                    Файл '%s' не найден
                                    
                                    """, fileName));
                                    bw.flush();
                                    return;
                                }

                                bw.write(String.format("""
                                Добавлены задачи из файла '%s'
                                
                                """, fileName));

                                bw.flush();
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
                                        
                    """);

            String input;
            bw.write("Введи команду\n> ");
            bw.flush();
            while ((input = br.readLine()) != null) {
                input = input.trim();

                if ("quit".equalsIgnoreCase(input)) {
                    bw.write("""
                            
                            Завершаю работу Трекера
                            
                            """);
                    return;
                }
                if ("help".equalsIgnoreCase(input)) {
                    bw.write("""
                                                
                            list    напечатать список задач
                            add     создать задачу
                            show    напечатать задачу
                            edit    редактировать задачу
                            delete  удалить задачу
                            
                            write   экспорт списка задач в файл
                            read    импорт списка задач из файла
                            
                            quit    завершить работу
                                                
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
                        bw.write("Введи постановку задачи\n> ");
                        bw.flush();
                        taskDefinition = br.readLine();
                        if (taskDefinition.trim().isEmpty()) {
                            bw.write("Значение не может быть пустым\n\n");
                            bw.flush();
                        }
                    }

                    String taskOwner = "";
                    while (taskOwner.trim().isEmpty()) {
                        bw.write("Введи имя владельца задачи\n> ");
                        bw.flush();
                        taskOwner = br.readLine();
                        if (taskOwner.trim().isEmpty()) {
                            bw.write("Значение не может быть пустым\n\n");
                            bw.flush();
                        }
                    }

                    Task task = new Task(taskDefinition, taskOwner);
                    tasksList.addTask(task);
                    bw.write(String.format("""
                            
                            Создана задача
                            %s
                            
                            """, task));

                } else if ("delete".equalsIgnoreCase(input)) {
                    if (tasksList.isEmpty()) {
                        bw.write("""
                                Список задач пуст
                                                            
                                """);
                    } else {
                        bw.write("""
                                                            
                                Удалить задачу
                                                            
                                """);

                        String taskId = "";
                        while (taskId.trim().isEmpty()) {
                            bw.write("Введи id задачи\n> ");
                            bw.flush();
                            taskId = br.readLine();
                            if (taskId.trim().isEmpty()) {
                                bw.write("Значение не может быть пустым\n\n");
                                bw.flush();
                                continue;
                            }
                            Task taskToDelete = tasksList.getTaskById(taskId);
                            if (taskToDelete == null) {
                                bw.write(String.format("""
                                        Задача
                                        id=%s
                                        не существует
                                                                            
                                        """, taskId));
                                bw.flush();
                                continue;
                            }
                            if (tasksList.delTask(taskToDelete)) {
                                bw.write(String.format("""
                                        Задача
                                        id=%s
                                        успешно удалена
                                                                            
                                        """, taskId));
                            } else {
                                bw.write(String.format("""
                                        Удаление задачи
                                        id=%s
                                        провалилось
                                                                            
                                        """, taskId));
                            }
                        }
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
                            bw.write("Введи имя файла\n> ");
                            bw.flush();
                            fileName = br.readLine();
                            if (fileName.trim().isEmpty()) {
                                bw.write("Значение не может быть пустым\n\n");
                                bw.flush();
                                continue;
                            }
                            tasksList.toStore(fileName);

                            bw.write(String.format("""
                                Задачи записаны в файл '%s'
                                
                                """, fileName));
                        }
                    }

                } else if ("read".equalsIgnoreCase(input)) {
                    bw.write("""
                            
                            Добавить задачи из файла
                            
                            """);

                    String fileName = "";
                    while (fileName.trim().isEmpty()) {
                        bw.write("Введи имя файла\n> ");
                        bw.flush();
                        fileName = br.readLine();
                        if (fileName.trim().isEmpty()) {
                            bw.write("Значение не может быть пустым\n\n");
                            bw.flush();
                            continue;
                        }
                        try {
                            tasksList.fromStore(fileName);
                        } catch (FileNotFoundException e) {
                            bw.write(String.format("""
                                    Файл '%s' не найден
                                    
                                    """, fileName));
                            bw.flush();
                            continue;
                        }

                        bw.write(String.format("""
                                Добавлены задачи из файла '%s'
                                
                                """, fileName));
                    }

                } else if ("edit".equalsIgnoreCase(input)) {
                    if (tasksList.isEmpty()) {
                        bw.write("""
                                Список задач пуст
                                                            
                                """);
                    } else {

                        bw.write("""
                                                            
                                Редактировать задачу
                                                            
                                """);

                        String taskId = "";
                        while (taskId.trim().isEmpty()) {
                            bw.write("Введи id задачи\n> ");
                            bw.flush();
                            taskId = br.readLine();
                            if (taskId.trim().isEmpty()) {
                                bw.write("Значение не может быть пустым\n\n");
                                bw.flush();
                                continue;
                            }
                            Task taskToEdit = tasksList.getTaskById(taskId);
                            if (taskToEdit == null) {
                                bw.write("Задача с id=" + taskId + " не существует");
                                bw.flush();
                                continue;
                            }

                            bw.write(String.format("""
                                    Задача
                                                                    
                                    %s
                                                                    
                                    Поля для редактирования
                                      definition - постановка
                                      owner      - владелец
                                      done       - выполнена/не выполнена
                                                            
                                    """, taskToEdit));
                            bw.flush();

                            String fieldName = "";
                            while (fieldName.trim().isEmpty()) {
                                bw.write("Введи имя поля для редактирования\n> ");
                                bw.flush();
                                fieldName = br.readLine();
                                if (fieldName.trim().isEmpty()) {
                                    bw.write("Значение не может быть пустым\n\n");
                                    bw.flush();
                                    continue;
                                }

                                if (fieldName.trim().equalsIgnoreCase("definition")) {
                                    String taskDefinition = "";
                                    while (taskDefinition.trim().isEmpty()) {
                                        bw.write("Введи постановку задачи\n> ");
                                        bw.flush();
                                        taskDefinition = br.readLine();
                                        if (taskDefinition.trim().isEmpty()) {
                                            bw.write("Значение не может быть пустым\n\n");
                                            bw.flush();
                                        }
                                    }
                                    taskToEdit.setDefinition(taskDefinition);

                                } else if (fieldName.trim().equalsIgnoreCase("owner")) {
                                    String taskOwner = "";
                                    while (taskOwner.trim().isEmpty()) {
                                        bw.write("Введи нового владельца задачи:\n> ");
                                        bw.flush();
                                        taskOwner = br.readLine();
                                        if (taskOwner.trim().isEmpty()) {
                                            bw.write("Значение не может быть пустым\n\n");
                                            bw.flush();
                                        }
                                    }
                                    taskToEdit.setDefinition(taskOwner);

                                } else if (fieldName.trim().equalsIgnoreCase("done")) {
                                    String taskDone = "";
                                    while (taskDone.trim().isEmpty()) {
                                        bw.write("""
                                                Введи + для установки статуса Выполнено
                                                Введи - для установки статуса Не выполнено
                                                > """);
                                        bw.flush();
                                        taskDone = br.readLine();
                                        if (!(taskDone.trim().equals("+") || taskDone.trim().equals("-"))) {
                                            bw.write("Получено некорректное значение\n\n");
                                            bw.flush();
                                        }
                                    }
                                    if (taskDone.trim().equals("+")) {
                                        taskToEdit.setDone(true);
                                    } else if (taskDone.trim().equals("-")) {
                                        taskToEdit.setDone(false);
                                    }
                                    bw.write("""
                                            Статус задачи изменен
                                                                                        
                                            """);
                                    bw.flush();
                                }
                            }
                        }
                    }
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
                                bw.write("Значение не может быть пустым\n\n");
                                bw.flush();
                                continue;
                            }
                            Task task = tasksList.getTaskById(taskId);
                            if (task == null) {
                                bw.write("Задача с id=" + taskId + " не существует\n\n");
                                bw.flush();
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
                    bw.write(String.format("""
                            '%s' не является командой
                            
                            """, input));
                    bw.flush();
                }

                bw.write("Введи команду\n> ");
                bw.flush();

           }

        } catch (IOException e) {
            be.write(String.format("""
                    Ошибка ввода / вывода
                    %s
                    
                    """, e));
            be.flush();
        }
    }
}