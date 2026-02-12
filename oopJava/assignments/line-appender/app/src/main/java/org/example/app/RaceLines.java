package org.example.app;

import org.example.impl1.LineWriterNoSync;
import org.example.impl2.LineWriterSync;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class RaceLines {
    /**
     * Несинхронизированная запись
     * хрупко
     */
    public static void writeNoSync(Path src1, Path src2, Path target) throws Exception {
        System.out.println("Несинхронизированная запись");


        // целевой файл
        Files.write(target, new byte[0]);

        // потоки
        LineWriterNoSync lineWriterNoSync = new LineWriterNoSync();

        // создание
        Thread thread1 = new Thread(() -> {
            try {
//                System.out.println(">> поток 1 начат");
                lineWriterNoSync.appendToFile(src1.toString(), target.toString());
//                System.out.println(">> поток 1 завершен");
            } catch (IOException | InterruptedException e) {
//                System.err.println(">> Ошибка потока 1" + e);
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
//                System.out.println(">> поток 2 начат");
                lineWriterNoSync.appendToFile(src2.toString(), target.toString());
//                System.out.println(">> поток 2 завершен");
            } catch (IOException | InterruptedException e) {
//                System.err.println(">> Ошибка потока 2" + e);
            }
        });

        // выполнение
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

    }

    /**
     * Синхронизированная запись
     * менее хрупко
     */
    public static void writeSync(Path src1, Path src2, Path target) throws Exception {
        System.out.println("Синхронизированная запись");


        // целевой файл
        Files.write(target, new byte[0]);

        // потоки
        LineWriterSync lineWriterSync = new LineWriterSync();

        // создание
        Thread thread1 = new Thread(() -> {
//            System.out.println(">> поток 1 начат");
            lineWriterSync.appendToFile(src1.toString(), target.toString());
//            System.out.println(">> поток 1 завершен");
        });

        Thread thread2 = new Thread(() -> {
//            System.out.println(">> поток 2 начат");
            lineWriterSync.appendToFile(src2.toString(), target.toString());
//            System.out.println(">> поток 2 завершен");
        });

        // выполнение
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

    }

    public static void main(String[] args) throws Exception {
        System.out.println("Демо хрупкого поведения");

        // файлы
        Path src1 = Files.createTempFile("src1", ".txt");
        Path src2 = Files.createTempFile("src2", ".txt");
        Path target = Files.createTempFile("target", ".txt");

        // файлы-источники

        Files.write(src1, (
                "файл1-строка1\n" +
                        "файл1-строка2\n" +
                        "файл1-строка3").getBytes());

        Files.write(src2,
                ("файл2-строка1\n" +
                        "файл2-строка2\n" +
                        "файл2-строка3\n" +
                        "файл2-строка4\n" +
                        "файл2-строка5").getBytes()
        );


        System.out.println("> Файл 1:");
        Files.readAllLines(src1).forEach(System.out::println);
        System.out.println("-------");


        System.out.println("> Файл 2:");
        Files.readAllLines(src2).forEach(System.out::println);
        System.out.println("-------");

        System.out.println();

        // NoSync
        RaceLines.writeNoSync(src1, src2, target);

        // результат
        System.out.println("\n> Целевой файл:");
        Files.readAllLines(target).forEach(System.out::println);
        System.out.println("-------");

        Files.deleteIfExists(target);

        // Sync
        RaceLines.writeSync(src1, src2, target);

        // результат
        System.out.println("\n> Целевой файл:");
        Files.readAllLines(target).forEach(System.out::println);
        System.out.println("-------");

        // чистка
        Files.deleteIfExists(src1);
        Files.deleteIfExists(src2);
        Files.deleteIfExists(target);

    }
}

/*
Демо хрупкого поведения
> Файл 1:
файл1-строка1
файл1-строка2
файл1-строка3
-------
> Файл 2:
файл2-строка1
файл2-строка2
файл2-строка3
файл2-строка4
файл2-строка5
-------

Несинхронизированная запись

> Целевой файл:
/tmp/src16547253953171378472.txt:1 файл1-строка1
/tmp/src16547253953171378472.txt:2 файл1-строка2
/tmp/src214091874434511705656.txt:1 файл2-строка1
/tmp/src16547253953171378472.txt:3 файл1-строка3
/tmp/src214091874434511705656.txt:2 файл2-строка2
/tmp/src214091874434511705656.txt:3 файл2-строка3
/tmp/src214091874434511705656.txt:4 файл2-строка4
/tmp/src214091874434511705656.txt:5 файл2-строка5
-------
Синхронизированная запись

> Целевой файл:
/tmp/src16547253953171378472.txt:1 файл1-строка1
/tmp/src16547253953171378472.txt:2 файл1-строка2
/tmp/src16547253953171378472.txt:3 файл1-строка3
/tmp/src214091874434511705656.txt:1 файл2-строка1
/tmp/src214091874434511705656.txt:2 файл2-строка2
/tmp/src214091874434511705656.txt:3 файл2-строка3
/tmp/src214091874434511705656.txt:4 файл2-строка4
/tmp/src214091874434511705656.txt:5 файл2-строка5
-------

 */