# Создание дистрибутива


## сборка проекта
- собираю проект вручную
  - повторяю скрипт maven
- все инструкции выполняю в каталоге проекта
- каталог проекта - это каталог, который содержит
  - каталог исходников - например, `src`
  - каталог скомпилированных классов - например, `target`

```shell
cd ~/projects/silly_bus/java_basics/popJava
```

### компиляция
- Maven компилирует все java-файлы проекта, но мне надо только один класс, выбираю точечно
- исходник - src/main/java/pop/lesson04/ScannerInput.java
- выходной каталог - target/classes

```shell
javac -verbose -d "target/classes" "src/main/java/pop/lesson04/ScannerInput.java"
```

<details>
  <summary>лог</summary>

```text
[parsing started SimpleFileObject[/home/xubuser/projects/silly_bus/java_basics/popJava/src/main/java/pop/lesson04/ScannerInput.java]]
[parsing completed 34ms]
[loading /modules/jdk.javadoc/module-info.class]
[loading /modules/jdk.jdwp.agent/module-info.class]
[loading /modules/jdk.incubator.vector/module-info.class]
[loading /modules/java.datatransfer/module-info.class]
[loading /modules/java.logging/module-info.class]
[loading /modules/jdk.security.auth/module-info.class]
[loading /modules/java.security.sasl/module-info.class]
[loading /modules/jdk.random/module-info.class]
[loading /modules/java.prefs/module-info.class]
[loading /modules/jdk.jdeps/module-info.class]
[loading /modules/java.base/module-info.class]
[loading /modules/jdk.jcmd/module-info.class]
[loading /modules/jdk.compiler/module-info.class]
[loading /modules/jdk.editpad/module-info.class]
[loading /modules/jdk.crypto.ec/module-info.class]
[loading /modules/jdk.management/module-info.class]
[loading /modules/jdk.internal.vm.compiler/module-info.class]
[loading /modules/java.se/module-info.class]
[loading /modules/java.xml.crypto/module-info.class]
[loading /modules/java.security.jgss/module-info.class]
[loading /modules/java.net.http/module-info.class]
[loading /modules/jdk.sctp/module-info.class]
[loading /modules/jdk.jconsole/module-info.class]
[loading /modules/jdk.dynalink/module-info.class]
[loading /modules/java.sql/module-info.class]
[loading /modules/jdk.jfr/module-info.class]
[loading /modules/java.xml/module-info.class]
[loading /modules/jdk.naming.rmi/module-info.class]
[loading /modules/jdk.internal.jvmstat/module-info.class]
[loading /modules/jdk.naming.dns/module-info.class]
[loading /modules/java.naming/module-info.class]
[loading /modules/jdk.zipfs/module-info.class]
[loading /modules/jdk.httpserver/module-info.class]
[loading /modules/jdk.unsupported/module-info.class]
[loading /modules/jdk.localedata/module-info.class]
[loading /modules/java.smartcardio/module-info.class]
[loading /modules/jdk.internal.ed/module-info.class]
[loading /modules/jdk.jpackage/module-info.class]
[loading /modules/java.desktop/module-info.class]
[loading /modules/jdk.internal.vm.ci/module-info.class]
[loading /modules/jdk.nio.mapmode/module-info.class]
[loading /modules/jdk.attach/module-info.class]
[loading /modules/jdk.crypto.cryptoki/module-info.class]
[loading /modules/jdk.internal.opt/module-info.class]
[loading /modules/java.management/module-info.class]
[loading /modules/jdk.jstatd/module-info.class]
[loading /modules/jdk.xml.dom/module-info.class]
[loading /modules/jdk.jlink/module-info.class]
[loading /modules/jdk.management.jfr/module-info.class]
[loading /modules/jdk.incubator.foreign/module-info.class]
[loading /modules/jdk.management.agent/module-info.class]
[loading /modules/jdk.unsupported.desktop/module-info.class]
[loading /modules/jdk.jsobject/module-info.class]
[loading /modules/jdk.charsets/module-info.class]
[loading /modules/jdk.jshell/module-info.class]
[loading /modules/java.scripting/module-info.class]
[loading /modules/jdk.net/module-info.class]
[loading /modules/jdk.accessibility/module-info.class]
[loading /modules/jdk.internal.le/module-info.class]
[loading /modules/java.rmi/module-info.class]
[loading /modules/java.management.rmi/module-info.class]
[loading /modules/java.compiler/module-info.class]
[loading /modules/jdk.internal.vm.compiler.management/module-info.class]
[loading /modules/jdk.jartool/module-info.class]
[loading /modules/jdk.jdi/module-info.class]
[loading /modules/java.sql.rowset/module-info.class]
[loading /modules/jdk.security.jgss/module-info.class]
[loading /modules/java.instrument/module-info.class]
[loading /modules/java.transaction.xa/module-info.class]
[loading /modules/jdk.hotspot.agent/module-info.class]
[search path for source files: .]
[search path for class files: /usr/lib/jvm/java-17-openjdk-amd64/lib/modules,.]
[loading /modules/java.base/java/io/PrintStream.class]
[loading /modules/java.logging/java/util/logging/Logger.class]
[loading /modules/java.base/java/lang/Object.class]
[loading /modules/java.base/java/util/LinkedHashSet.class]
[loading /modules/java.base/java/lang/String.class]
[loading /modules/java.base/java/util/Scanner.class]
[loading /modules/java.base/java/util/SortedMap.class]
[loading /modules/java.base/java/lang/Integer.class]
[loading /modules/java.base/java/lang/Deprecated.class]
[loading /modules/java.base/java/lang/annotation/Retention.class]
[loading /modules/java.base/java/lang/annotation/RetentionPolicy.class]
[loading /modules/java.base/java/lang/annotation/Target.class]
[loading /modules/java.base/java/lang/annotation/ElementType.class]
[checking pop.lesson04.ScannerInput]
[loading /modules/java.base/java/io/Serializable.class]
[loading /modules/java.base/java/lang/AutoCloseable.class]
[loading /modules/java.base/java/lang/Class.class]
[loading /modules/java.base/java/lang/reflect/GenericDeclaration.class]
[loading /modules/java.base/java/lang/reflect/AnnotatedElement.class]
[loading /modules/java.base/java/lang/reflect/Type.class]
[loading /modules/java.base/java/lang/invoke/TypeDescriptor.class]
[loading /modules/java.base/java/lang/invoke/TypeDescriptor$OfField.class]
[loading /modules/java.base/java/lang/constant/Constable.class]
[loading /modules/java.base/java/util/Collection.class]
[loading /modules/java.base/java/util/HashSet.class]
[loading /modules/java.base/java/util/AbstractSet.class]
[loading /modules/java.base/java/util/AbstractCollection.class]
[loading /modules/java.base/java/lang/Iterable.class]
[loading /modules/java.base/java/util/Set.class]
[loading /modules/java.base/java/lang/Cloneable.class]
[loading /modules/java.base/java/util/Spliterator.class]
[loading /modules/java.base/java/util/Iterator.class]
[loading /modules/java.base/java/util/stream/Stream.class]
[loading /modules/java.base/java/util/function/Predicate.class]
[loading /modules/java.base/java/util/function/IntFunction.class]
[loading /modules/java.base/java/util/function/Consumer.class]
[loading /modules/java.base/java/lang/Appendable.class]
[loading /modules/java.base/java/io/Closeable.class]
[loading /modules/java.base/java/io/FilterOutputStream.class]
[loading /modules/java.base/java/io/OutputStream.class]
[loading /modules/java.base/java/io/Flushable.class]
[loading /modules/java.base/java/lang/Comparable.class]
[loading /modules/java.base/java/lang/CharSequence.class]
[loading /modules/java.base/java/lang/constant/ConstantDesc.class]
[loading /modules/java.base/java/lang/Number.class]
[loading /modules/java.base/java/lang/NumberFormatException.class]
[loading /modules/java.base/java/lang/IllegalArgumentException.class]
[loading /modules/java.base/java/lang/RuntimeException.class]
[loading /modules/java.base/java/lang/Exception.class]
[loading /modules/java.base/java/lang/Throwable.class]
[loading /modules/java.base/java/util/StringJoiner.class]
[loading /modules/java.base/java/lang/Byte.class]
[loading /modules/java.base/java/lang/Character.class]
[loading /modules/java.base/java/lang/Short.class]
[loading /modules/java.base/java/lang/Long.class]
[loading /modules/java.base/java/lang/Float.class]
[loading /modules/java.base/java/lang/Double.class]
[loading /modules/java.base/java/lang/Boolean.class]
[loading /modules/java.base/java/lang/Void.class]
[loading /modules/java.base/java/util/Map.class]
[loading /modules/java.base/java/lang/System.class]
[loading /modules/java.base/java/io/InputStream.class]
[loading /modules/java.base/java/nio/channels/ReadableByteChannel.class]
[loading /modules/java.base/java/nio/file/Path.class]
[loading /modules/java.base/java/io/File.class]
[loading /modules/java.base/java/lang/Readable.class]
[loading /modules/java.base/java/util/TreeMap.class]
[loading /modules/java.base/java/util/Comparator.class]
[loading /modules/java.base/java/util/AbstractMap.class]
[loading /modules/java.base/java/util/NavigableMap.class]
[loading /modules/java.base/java/lang/IllegalStateException.class]
[loading /modules/java.base/java/util/Locale.class]
[loading /modules/java.base/java/lang/Error.class]
[wrote target/classes/pop/lesson04/ScannerInput$1.class]
[wrote target/classes/pop/lesson04/ScannerInput$2.class]
[wrote target/classes/pop/lesson04/ScannerInput$3.class]
[wrote target/classes/pop/lesson04/ScannerInput$4.class]
[loading /modules/java.base/java/lang/invoke/StringConcatFactory.class]
[loading /modules/java.base/java/lang/invoke/MethodHandles.class]
[loading /modules/java.base/java/lang/invoke/MethodHandles$Lookup.class]
[loading /modules/java.base/java/lang/invoke/MethodType.class]
[loading /modules/java.base/java/lang/invoke/CallSite.class]
[wrote target/classes/pop/lesson04/ScannerInput.class]
[total 507ms]
```

</details>


### сборка jar
- указываю --main-class

```shell
jar --verbose --create --file /tmp/scanner_app.jar --main-class pop.lesson04.ScannerInput -C target/classes .
```


<details>
  <summary>лог</summary>
```text
added manifest
adding: pop/(in = 0) (out= 0)(stored 0%)
adding: pop/lesson04/(in = 0) (out= 0)(stored 0%)
adding: pop/lesson04/ScannerInput$1.class(in = 482) (out= 337)(deflated 30%)
adding: pop/lesson04/ScannerInput$2.class(in = 521) (out= 357)(deflated 31%)
adding: pop/lesson04/ScannerInput$3.class(in = 524) (out= 359)(deflated 31%)
adding: pop/lesson04/ScannerInput$4.class(in = 524) (out= 362)(deflated 30%)
adding: pop/lesson04/ScannerInput.class(in = 5422) (out= 2612)(deflated 51%)
```
</details>

### тестовый запуск


<details>
  <summary>лог</summary>

```text
2025-09-14T02:30:08 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ java -jar /tmp/scanner_app.jar 
Программа создает словарь имя->возраст
и позволяет добавить записи в словарь

Чтобы начать добавление записи, выполни /add
Чтобы посмотреть существующие записи, выполни /show
Для вывода всех поддерживаемых комманд, выполни /help

Введи команду, нажми Enter (/help для отображения доступных команд): /h
==========
Поддерживаемые команды

добавить запись:
/add
/a
/new
/n

напечатать сохраненные записи:
/print
/p
/show
/s

завершить работу приложения:
/exit
/ex
/quit
/q

напечатать это сообщение:
/help
/h
Введи команду, нажми Enter (/help для отображения доступных команд): /a
Введи имя пользователя, нажми Enter: username
Введи возраст, нажми Enter: 55
Введи команду, нажми Enter (/help для отображения доступных команд): /p
{username=55}Введи команду, нажми Enter (/help для отображения доступных команд): /a
Введи имя пользователя, нажми Enter: user2
Введи возраст, нажми Enter: 12
Введи команду, нажми Enter (/help для отображения доступных команд): /p
{user2=12, username=55}Введи команду, нажми Enter (/help для отображения доступных команд): /exit
2025-09-14T02:31:34 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ 
```
</details>

### трудности
- не учел бинарники "с долларом" - ScannerInput$1.class
  - эти файлы - специальные анонимные внутренние классы, сгенерированные компилятором

---

<details>
  <summary>лог bash-скрипта</summary>

```text
2025-09-14T02:50:29 ~/projects/silly_bus/java_basics/popJava/src/main/java/pop/lesson06  (lesson_06)₽ /bin/bash /home/xubuser/projects/silly_bus/java_basics/popJava/src/main/java/pop/lesson06/build_scanner.sh
рабочая директория
/home/xubuser/projects/silly_bus/java_basics/popJava
компиляция
[parsing started SimpleFileObject[/home/xubuser/projects/silly_bus/java_basics/popJava/src/main/java/pop/lesson04/ScannerInput.java]]
[parsing completed 35ms]
[loading /modules/jdk.javadoc/module-info.class]
[loading /modules/jdk.jdwp.agent/module-info.class]
[loading /modules/jdk.incubator.vector/module-info.class]
[loading /modules/java.datatransfer/module-info.class]
[loading /modules/java.logging/module-info.class]
[loading /modules/jdk.security.auth/module-info.class]
[loading /modules/java.security.sasl/module-info.class]
[loading /modules/jdk.random/module-info.class]
[loading /modules/java.prefs/module-info.class]
[loading /modules/jdk.jdeps/module-info.class]
[loading /modules/java.base/module-info.class]
[loading /modules/jdk.jcmd/module-info.class]
[loading /modules/jdk.compiler/module-info.class]
[loading /modules/jdk.editpad/module-info.class]
[loading /modules/jdk.crypto.ec/module-info.class]
[loading /modules/jdk.management/module-info.class]
[loading /modules/jdk.internal.vm.compiler/module-info.class]
[loading /modules/java.se/module-info.class]
[loading /modules/java.xml.crypto/module-info.class]
[loading /modules/java.security.jgss/module-info.class]
[loading /modules/java.net.http/module-info.class]
[loading /modules/jdk.sctp/module-info.class]
[loading /modules/jdk.jconsole/module-info.class]
[loading /modules/jdk.dynalink/module-info.class]
[loading /modules/java.sql/module-info.class]
[loading /modules/jdk.jfr/module-info.class]
[loading /modules/java.xml/module-info.class]
[loading /modules/jdk.naming.rmi/module-info.class]
[loading /modules/jdk.internal.jvmstat/module-info.class]
[loading /modules/jdk.naming.dns/module-info.class]
[loading /modules/java.naming/module-info.class]
[loading /modules/jdk.zipfs/module-info.class]
[loading /modules/jdk.httpserver/module-info.class]
[loading /modules/jdk.unsupported/module-info.class]
[loading /modules/jdk.localedata/module-info.class]
[loading /modules/java.smartcardio/module-info.class]
[loading /modules/jdk.internal.ed/module-info.class]
[loading /modules/jdk.jpackage/module-info.class]
[loading /modules/java.desktop/module-info.class]
[loading /modules/jdk.internal.vm.ci/module-info.class]
[loading /modules/jdk.nio.mapmode/module-info.class]
[loading /modules/jdk.attach/module-info.class]
[loading /modules/jdk.crypto.cryptoki/module-info.class]
[loading /modules/jdk.internal.opt/module-info.class]
[loading /modules/java.management/module-info.class]
[loading /modules/jdk.jstatd/module-info.class]
[loading /modules/jdk.xml.dom/module-info.class]
[loading /modules/jdk.jlink/module-info.class]
[loading /modules/jdk.management.jfr/module-info.class]
[loading /modules/jdk.incubator.foreign/module-info.class]
[loading /modules/jdk.management.agent/module-info.class]
[loading /modules/jdk.unsupported.desktop/module-info.class]
[loading /modules/jdk.jsobject/module-info.class]
[loading /modules/jdk.charsets/module-info.class]
[loading /modules/jdk.jshell/module-info.class]
[loading /modules/java.scripting/module-info.class]
[loading /modules/jdk.net/module-info.class]
[loading /modules/jdk.accessibility/module-info.class]
[loading /modules/jdk.internal.le/module-info.class]
[loading /modules/java.rmi/module-info.class]
[loading /modules/java.management.rmi/module-info.class]
[loading /modules/java.compiler/module-info.class]
[loading /modules/jdk.internal.vm.compiler.management/module-info.class]
[loading /modules/jdk.jartool/module-info.class]
[loading /modules/jdk.jdi/module-info.class]
[loading /modules/java.sql.rowset/module-info.class]
[loading /modules/jdk.security.jgss/module-info.class]
[loading /modules/java.instrument/module-info.class]
[loading /modules/java.transaction.xa/module-info.class]
[loading /modules/jdk.hotspot.agent/module-info.class]
[search path for source files: .]
[search path for class files: /usr/lib/jvm/java-17-openjdk-amd64/lib/modules,.]
[loading /modules/java.base/java/io/PrintStream.class]
[loading /modules/java.logging/java/util/logging/Logger.class]
[loading /modules/java.base/java/lang/Object.class]
[loading /modules/java.base/java/util/LinkedHashSet.class]
[loading /modules/java.base/java/lang/String.class]
[loading /modules/java.base/java/util/Scanner.class]
[loading /modules/java.base/java/util/SortedMap.class]
[loading /modules/java.base/java/lang/Integer.class]
[loading /modules/java.base/java/lang/Deprecated.class]
[loading /modules/java.base/java/lang/annotation/Retention.class]
[loading /modules/java.base/java/lang/annotation/RetentionPolicy.class]
[loading /modules/java.base/java/lang/annotation/Target.class]
[loading /modules/java.base/java/lang/annotation/ElementType.class]
[checking pop.lesson04.ScannerInput]
[loading /modules/java.base/java/io/Serializable.class]
[loading /modules/java.base/java/lang/AutoCloseable.class]
[loading /modules/java.base/java/lang/Class.class]
[loading /modules/java.base/java/lang/reflect/GenericDeclaration.class]
[loading /modules/java.base/java/lang/reflect/AnnotatedElement.class]
[loading /modules/java.base/java/lang/reflect/Type.class]
[loading /modules/java.base/java/lang/invoke/TypeDescriptor.class]
[loading /modules/java.base/java/lang/invoke/TypeDescriptor$OfField.class]
[loading /modules/java.base/java/lang/constant/Constable.class]
[loading /modules/java.base/java/util/Collection.class]
[loading /modules/java.base/java/util/HashSet.class]
[loading /modules/java.base/java/util/AbstractSet.class]
[loading /modules/java.base/java/util/AbstractCollection.class]
[loading /modules/java.base/java/lang/Iterable.class]
[loading /modules/java.base/java/util/Set.class]
[loading /modules/java.base/java/lang/Cloneable.class]
[loading /modules/java.base/java/util/Spliterator.class]
[loading /modules/java.base/java/util/Iterator.class]
[loading /modules/java.base/java/util/stream/Stream.class]
[loading /modules/java.base/java/util/function/Predicate.class]
[loading /modules/java.base/java/util/function/IntFunction.class]
[loading /modules/java.base/java/util/function/Consumer.class]
[loading /modules/java.base/java/lang/Appendable.class]
[loading /modules/java.base/java/io/Closeable.class]
[loading /modules/java.base/java/io/FilterOutputStream.class]
[loading /modules/java.base/java/io/OutputStream.class]
[loading /modules/java.base/java/io/Flushable.class]
[loading /modules/java.base/java/lang/Comparable.class]
[loading /modules/java.base/java/lang/CharSequence.class]
[loading /modules/java.base/java/lang/constant/ConstantDesc.class]
[loading /modules/java.base/java/lang/Number.class]
[loading /modules/java.base/java/lang/NumberFormatException.class]
[loading /modules/java.base/java/lang/IllegalArgumentException.class]
[loading /modules/java.base/java/lang/RuntimeException.class]
[loading /modules/java.base/java/lang/Exception.class]
[loading /modules/java.base/java/lang/Throwable.class]
[loading /modules/java.base/java/util/StringJoiner.class]
[loading /modules/java.base/java/lang/Byte.class]
[loading /modules/java.base/java/lang/Character.class]
[loading /modules/java.base/java/lang/Short.class]
[loading /modules/java.base/java/lang/Long.class]
[loading /modules/java.base/java/lang/Float.class]
[loading /modules/java.base/java/lang/Double.class]
[loading /modules/java.base/java/lang/Boolean.class]
[loading /modules/java.base/java/lang/Void.class]
[loading /modules/java.base/java/util/Map.class]
[loading /modules/java.base/java/lang/System.class]
[loading /modules/java.base/java/io/InputStream.class]
[loading /modules/java.base/java/nio/channels/ReadableByteChannel.class]
[loading /modules/java.base/java/nio/file/Path.class]
[loading /modules/java.base/java/io/File.class]
[loading /modules/java.base/java/lang/Readable.class]
[loading /modules/java.base/java/util/TreeMap.class]
[loading /modules/java.base/java/util/Comparator.class]
[loading /modules/java.base/java/util/AbstractMap.class]
[loading /modules/java.base/java/util/NavigableMap.class]
[loading /modules/java.base/java/lang/IllegalStateException.class]
[loading /modules/java.base/java/util/Locale.class]
[loading /modules/java.base/java/lang/Error.class]
[wrote target/classes/pop/lesson04/ScannerInput$1.class]
[wrote target/classes/pop/lesson04/ScannerInput$2.class]
[wrote target/classes/pop/lesson04/ScannerInput$3.class]
[wrote target/classes/pop/lesson04/ScannerInput$4.class]
[loading /modules/java.base/java/lang/invoke/StringConcatFactory.class]
[loading /modules/java.base/java/lang/invoke/MethodHandles.class]
[loading /modules/java.base/java/lang/invoke/MethodHandles$Lookup.class]
[loading /modules/java.base/java/lang/invoke/MethodType.class]
[loading /modules/java.base/java/lang/invoke/CallSite.class]
[wrote target/classes/pop/lesson04/ScannerInput.class]
[total 745ms]
сборка jar
added manifest
adding: pop/(in = 0) (out= 0)(stored 0%)
adding: pop/lesson04/(in = 0) (out= 0)(stored 0%)
adding: pop/lesson04/ScannerInput$1.class(in = 482) (out= 337)(deflated 30%)
adding: pop/lesson04/ScannerInput$2.class(in = 521) (out= 357)(deflated 31%)
adding: pop/lesson04/ScannerInput$3.class(in = 524) (out= 359)(deflated 31%)
adding: pop/lesson04/ScannerInput$4.class(in = 524) (out= 362)(deflated 30%)
adding: pop/lesson04/ScannerInput.class(in = 5422) (out= 2612)(deflated 51%)
сформированный jar
/tmp/scanner_app.jar: Java archive data (JAR)
содержимое сформированного jar
Archive:  /tmp/scanner_app.jar
  Length      Date    Time    Name
---------  ---------- -----   ----
        0  2025-09-14 07:52   META-INF/
       94  2025-09-14 07:52   META-INF/MANIFEST.MF
        0  2025-09-14 06:19   pop/
        0  2025-09-14 06:19   pop/lesson04/
      482  2025-09-14 07:52   pop/lesson04/ScannerInput$1.class
      521  2025-09-14 07:52   pop/lesson04/ScannerInput$2.class
      524  2025-09-14 07:52   pop/lesson04/ScannerInput$3.class
      524  2025-09-14 07:52   pop/lesson04/ScannerInput$4.class
     5422  2025-09-14 07:52   pop/lesson04/ScannerInput.class
---------                     -------
     7567                     9 files
2025-09-14T02:52:34 ~/projects/silly_bus/java_basics/popJava/src/main/java/pop/lesson06  (lesson_06)₽ java -jar /tmp/scanner_app.jar 
Программа создает словарь имя->возраст
и позволяет добавить записи в словарь

Чтобы начать добавление записи, выполни /add
Чтобы посмотреть существующие записи, выполни /show
Для вывода всех поддерживаемых комманд, выполни /help

Введи команду, нажми Enter (/help для отображения доступных команд): /ex
2025-09-14T02:52:58 ~/projects/silly_bus/java_basics/popJava/src/main/java/pop/lesson06  (lesson_06)₽ 
```

</details>


---

<details>
  <summary>неудачные попытки</summary>

### попытка 1

#### компиляция исходного кода Java

```text
2025-09-13T08:45:09 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ tree -L 1
.
├── pom.xml
├── README.md
├── seLFaq.md
├── src
├── SUGGESTIONS.md
└── target
```

- исходный код находится в src
- структура проекта диктует хранить байт код в каталоге `target`
- параметры компилятора `javac`
```text
Usage: javac <options> <source files>
...
-d <directory>               Specify where to place generated class files
```
- составляю и выполняю команду компиляции
`javac -d "target/classes/pop/lesson04" "src/main/java/pop/lesson04/ScannerInput.java"`

- компиляция выполнена без ошибок
- выходную директорию указал неверно - лишняя вложенность
  - предполагаю, компилятор за исходный путь принимает всю иерархию каталога `java`
```text
2025-09-13T08:51:38 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ tree target/classes/pop/lesson04
target/classes/pop/lesson04
├── pop
│   └── lesson04
│       ├── ScannerInput$1.class
│       ├── ScannerInput$2.class
│       ├── ScannerInput$3.class
│       ├── ScannerInput$4.class
│       └── ScannerInput.class
├── ScannerInput$1.class
├── ScannerInput$2.class
├── ScannerInput$3.class
├── ScannerInput$4.class
└── ScannerInput.class
```

- удаляю некорректные файлы
```text
2025-09-13T08:53:01 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ rm -rf target/classes/pop/lesson04/pop/
2025-09-13T08:55:33 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ tree target/classes/pop/lesson04
target/classes/pop/lesson04
├── ScannerInput$1.class
├── ScannerInput$2.class
├── ScannerInput$3.class
├── ScannerInput$4.class
└── ScannerInput.class
```

- корректирую путь и повторяю компиляцию
```shell
javac -d "target/classes" "src/main/java/pop/lesson04/ScannerInput.java"
```

```text
2025-09-13T08:55:35 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ javac -d "target/classes" "src/main/java/pop/lesson04/ScannerInput.java"
2025-09-13T08:56:50 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ tree -D target/classes/pop/lesson04
target/classes/pop/lesson04
├── [Sep 13 13:56]  ScannerInput$1.class
├── [Sep 13 13:56]  ScannerInput$2.class
├── [Sep 13 13:56]  ScannerInput$3.class
├── [Sep 13 13:56]  ScannerInput$4.class
└── [Sep 13 13:56]  ScannerInput.class
```

#### создание архива сборки приложения / JAR file

- интерфейс программы `jar` напоминает интерфейс `tar`
- учитываю параметры
```text
  -C DIR                     Change to the specified directory and include the
                             following file
  -f, --file=FILE            The archive file name. When omitted, either stdin or
                             stdout is used based on the operation
      --release VERSION      Places all following files in a versioned directory
                             of the jar (i.e. META-INF/versions/VERSION/)
  -v, --verbose              Generate verbose output on standard output
```
- составляю и выполняю команду создания файла jar
`jar --create --file /tmp/scanner_input.jar -C target/classes/pop/lesson04 ScannerInput.class`

```text
2025-09-13T09:11:08 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ ll /tmp/*.jar
-rw-rw-r-- 1 xubuser xubuser 3005 сен 13 14:10 /tmp/scanner_input.jar
```

#### выполнение из файла jar

- выполняю
`java /tmp/scanner_input.jar`

- ошибка
```text
Error: Could not find or load main class scanner_input.jar
Caused by: java.lang.ClassNotFoundException: scanner_input.jar
```

- программа `jar` зависает при попытке прочитать содержимое
```text
2025-09-13T09:19:28 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ jar -t /tmp/scanner_input.jar

```

- создаю jar заново из директории байт-кода
`cd target/classes/pop/lesson04`
- составляю и выполняю команду создания файла jar
`jar --create --file /tmp/scanner_input.jar ScannerInput.class`

```text
2025-09-13T09:31:29 ~/projects/silly_bus/java_basics/popJava/target/classes/pop/lesson04  (lesson_06)₽ ll -t /tmp/scanner_input.jar 
-rw-rw-r-- 1 xubuser xubuser 3005 сен 13 14:30 /tmp/scanner_input.jar
```

- та же проблема
```text
2025-09-13T09:32:08 ~/projects/silly_bus/java_basics/popJava/target/classes/pop/lesson04  (lesson_06)₽ jar -t /tmp/scanner_input.jar 
^C
2025-09-13T09:32:15 ~/projects/silly_bus/java_basics/popJava/target/classes/pop/lesson04  (lesson_06)₽ java /tmp/scanner_input.jar 
Error: Could not find or load main class .tmp.scanner_input.jar
Caused by: java.lang.ClassNotFoundException: /tmp/scanner_input/jar
2025-09-13T09:32:27 ~/projects/silly_bus/java_basics/popJava/target/classes/pop/lesson04  (lesson_06)₽ cd /tmp/
2025-09-13T09:32:33 /tmp ₽ java scanner_input.jar 
Error: Could not find or load main class scanner_input.jar
Caused by: java.lang.ClassNotFoundException: scanner_input.jar
```

- выполинл процедуру создания jar в каталоге бинарников, проблема не в путях
  - гипотеза - проблема в наименовании класса, должно быть `Main`
```text
2025-09-13T09:36:04 ~/projects/silly_bus/java_basics/popJava/target/classes/pop/lesson04  (lesson_06)₽ tar cf scanner-input.jar ScannerInput.class 
2025-09-13T09:36:32 ~/projects/silly_bus/java_basics/popJava/target/classes/pop/lesson04  (lesson_06)₽ ll
total 44
drwxrwxr-x 6 xubuser xubuser  4096 сен 13 13:38  ..
-rw-rw-r-- 1 xubuser xubuser   482 сен 13 13:56 'ScannerInput$1.class'
-rw-rw-r-- 1 xubuser xubuser   524 сен 13 13:56 'ScannerInput$3.class'
-rw-rw-r-- 1 xubuser xubuser   521 сен 13 13:56 'ScannerInput$2.class'
-rw-rw-r-- 1 xubuser xubuser   524 сен 13 13:56 'ScannerInput$4.class'
-rw-rw-r-- 1 xubuser xubuser  5245 сен 13 13:56  ScannerInput.class
drwxrwxr-x 2 xubuser xubuser  4096 сен 13 14:36  .
-rw-rw-r-- 1 xubuser xubuser 10240 сен 13 14:36  scanner-input.jar
2025-09-13T09:36:34 ~/projects/silly_bus/java_basics/popJava/target/classes/pop/lesson04  (lesson_06)₽ java scanner-input.jar
Error: Could not find or load main class scanner-input.jar
Caused by: java.lang.ClassNotFoundException: scanner-input.jar
```

---

### попытка 2

- выполняю
  `java /tmp/scanner_input.jar`

#### компиляция исходного кода Java

```text
2025-09-13T09:41:38 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ tree -L 1 -D
.
├── [Sep 13 08:33]  pom.xml
├── [Sep 13 13:38]  README.md
├── [Sep 13 08:33]  seLFaq.md
├── [Sep 13 08:33]  src
├── [Sep 13 08:33]  SUGGESTIONS.md
└── [Aug 22 08:39]  target
```

```text
2025-09-13T09:43:10 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ ls -l src/main/java/pop/lesson04/*
-rw-rw-r-- 1 xubuser xubuser 7038 сен 13 14:41 src/main/java/pop/lesson04/Main.java
-rw-rw-r-- 1 xubuser xubuser 4299 сен 13 09:37 src/main/java/pop/lesson04/README.md
-rw-rw-r-- 1 xubuser xubuser 7046 сен 13 09:37 src/main/java/pop/lesson04/ScannerInput.java
```

- составляю и выполняю команду компиляции
`javac -d "target/classes/pop/lesson04" "src/main/java/pop/lesson04/Main.java"`
  
```text
2025-09-13T09:45:28 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ javac -d "target/classes" "src/main/java/pop/lesson04/Main.java"
2025-09-13T09:46:00 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ ll target/classes/pop/lesson04
total 32
drwxrwxr-x 3 xubuser xubuser 4096 сен 13 14:45  ..
-rw-rw-r-- 1 xubuser xubuser  500 сен 13 14:45 'Main$4.class'
-rw-rw-r-- 1 xubuser xubuser  500 сен 13 14:45 'Main$3.class'
-rw-rw-r-- 1 xubuser xubuser  497 сен 13 14:45 'Main$2.class'
-rw-rw-r-- 1 xubuser xubuser  458 сен 13 14:45 'Main$1.class'
drwxrwxr-x 2 xubuser xubuser 4096 сен 13 14:45  .
-rw-rw-r-- 1 xubuser xubuser 5197 сен 13 14:45  Main.class
```

- составляю и выполняю команду создания файла jar
  `jar --create --file /tmp/scanner_input.jar -C target/classes/pop/lesson04 Main.class`

```text
2025-09-13T09:47:27 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ ll /tmp/scanner_input.jar 
-rw-rw-r-- 1 xubuser xubuser 2987 сен 13 14:47 /tmp/scanner_input.jar
```

#### выполнение из файла jar
- была ошибка в том, что я не указывал java-машине имя параметра `-jar`
- выполняю `java -jar /tmp/scanner_input.jar`
```text
2025-09-13T09:51:01 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ java -jar /tmp/scanner_input.jar 
no main manifest attribute, in /tmp/scanner_input.jar
```

- не найден файл манифеста, хотя, по доке, вызов `jar cf jar-file input-file(s)` должен был сгенерить манифест
  - > The command will also generate a default manifest file for the JAR archive.

- проверяю содержимое архива
```text
2025-09-13T14:35:33 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ unzip -l /tmp/scanner_input.jar 
Archive:  /tmp/scanner_input.jar
  Length      Date    Time    Name
---------  ---------- -----   ----
        0  2025-09-13 14:47   META-INF/
       55  2025-09-13 14:47   META-INF/MANIFEST.MF
     5197  2025-09-13 14:45   Main.class
---------                     -------
     5252                     3 files
```

- содержимое манифеста
```text
2025-09-13T14:43:01 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ unzip -p /tmp/scanner_input.jar META-INF/MANIFEST.MF | cat
Manifest-Version: 1.0
Created-By: 17.0.15 (Ubuntu)
```

- проблема - не указана точка входа программы
- создаю манифест
```shell
echo "Main-Class: pop.lesson04.ScannerInput" > src/main/java/pop/lesson06/Manifest.txt
```

- повторяю процедуру создания jar

```shell
javac -d "target/classes" "src/main/java/pop/lesson04/ScannerInput.java"
```
```shell
jar --create --file /tmp/scanner_input.jar --manifest src/main/java/pop/lesson06/Manifest.txt -C target/classes/pop/lesson04 ScannerInput.class
```
```shell
java -jar /tmp/scanner_input.jar
```

- ошибка связана с именем класса
```text
2025-09-13T15:04:13 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ java -jar /tmp/scanner_input.jar
Error: Could not find or load main class pop.lesson04.ScannerInput
```

- передаю имя класса в jar с помощью параметра `--main-class`
```shell
jar --create --file /tmp/scanner_input.jar --main-class=ScannerInput target/classes/pop/lesson04/ScannerInput.class
```

```text
2025-09-13T15:15:43 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ jar --create --file /tmp/scanner_input.jar --main-class=ScannerInput target/classes/pop/lesson04/ScannerInput.class
2025-09-13T15:15:50 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ ll /tmp/scanner_input.jar 
-rw-rw-r-- 1 xubuser xubuser 3087 сен 13 20:15 /tmp/scanner_input.jar
2025-09-13T15:16:05 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ unzip -p /tmp/scanner_input.jar META-INF/MANIFEST.MF | cat
Manifest-Version: 1.0
Created-By: 17.0.15 (Ubuntu)
Main-Class: ScannerInput
```

- не найден или не загружен основной класс
```text
2025-09-13T15:17:03 ~/projects/silly_bus/java_basics/popJava  (lesson_06)₽ java -jar /tmp/scanner_input.jar
Error: Could not find or load main class ScannerInput
Caused by: java.lang.ClassNotFoundException: ScannerInput
```


### попытка 3





### тестовый запуск

```text
java -jar /tmp/scanner_app.jar 
Error: Unable to initialize main class pop.lesson04.ScannerInput
Caused by: java.lang.NoClassDefFoundError: pop/lesson04/ScannerInput$1
```


### сборка jar
- указываю --main-class

```shell
jar --verbose --create --file /tmp/scanner_app.jar -C target/classes/pop/lesson04 ScannerInput.class
```

<details>
  <summary>лог</summary>

```text
jar --verbose --create --file /tmp/scanner_app.jar --main-class pop.lesson04.ScannerInput -C target/classes pop/lesson04/*.class
added manifest
adding: ScannerInput.class(in = 5422) (out= 2612)(deflated 51%)
```

</details>

### тестовый запуск

```text
java -jar /tmp/scanner_app.jar 
Error: Unable to initialize main class pop.lesson04.ScannerInput
Caused by: java.lang.NoClassDefFoundError: pop/lesson04/ScannerInput$1
```


</details>