### 8. Работа с файлами: Сохранение данных между запусками
**Цель**: Читать/записывать файлы, **опираясь на обработку ошибок из п.4**.  
**Задания** (используют Map из п.3 и try/catch из п.4):
1. Запишите данные из `Map` в `tasks.csv`.
2. Обработайте `FileNotFoundException` через `try/catch`.

---

## ход работы
- генерируемые файлы содержат информацию, которая воспроизводятся с помощью исходного кода
  - генерируемые файлы не включаю в систему контроля версий
  - в зависимости от задачи применяю временную файловую систему, чтобы обеспечить чистку диска
- генерируемые файлы размещаю в каталоге `target`
  - довод 1 - `target/` уже содержит генерируемые файлы - байт-код классов
  - довод 2 - `target/` включен в `.gitignore` как один из временных каталогов _Maven_

---

## Контрольные вопросы
- ❓ Почему `FileWriter` без `true` перезаписывает файл?
  - конструктор класса `FileWriter` имеет boolean параметр `append`
    - `append - if true, then bytes will be written to the end of the file rather than the beginning`
    - при `append == false` происходит запись в файл, по аналогии с перенаправлением вывода `>` в консоли
    - при `append == true` происходит дозапись в файл, по аналогии с перенаправлением вывода `>>` в консоли
- ❓ Как безопасно закрыть `BufferedReader`?  
  - 

## вопросы

### уточнить задачу
- при записи создавать новый файл?
- абсолютное имя файла - константа?
  - или запросить имя файла у пользователя?
  - задать переменной окружения?
- задание говорит записывать в файл `tasks.csv`
  - по суффиксу имени предполагаю, что файла типа "значения, разделенные запятой"
  - какой разделитель использовать?
  - в файл записывать текстовое или байтовое представление?
    - если текстовое, то как форматировать?


### в контрольных вопросах спрашивается о `BufferedReader`
  - вопрос верно поставлен?
  - не перепутали с `BufferedWriter`?


### в документации класса приведено несколько конструкторов
[пример](https://docs.oracle.com/javase/8/docs/api/java/io/FileWriter.html)

<details>
  <summary>конструкторы класса FileWriter</summary>

```text
Constructor Summary
Constructors Constructor 	Description
FileWriter(File file) 	
Constructs a FileWriter object given a File object.
FileWriter(File file, boolean append) 	
Constructs a FileWriter object given a File object.
FileWriter(FileDescriptor fd) 	
Constructs a FileWriter object associated with a file descriptor.
FileWriter(String fileName) 	
Constructs a FileWriter object given a file name.
FileWriter(String fileName, boolean append) 	
Constructs a FileWriter object given a file name with a boolean indicating whether or not to append the data written.
```

</details>

- почему конструкторов несколько?
  - это варианты использования?
  - это разные реализации конструктора?
    - но как они существуют, имея одинаковые имена?
- как интерпретировать эту информацию?
    - беру любой подходящий вариант?
    - все ли варианты приведены в доке?
