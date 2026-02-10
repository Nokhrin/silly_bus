package org.example.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class RaceCondition {
    /**
     * Несинхронизированная запись
     * хрупко
     */
    public static void writeNoSync() throws Exception {
        System.out.println("Несинхронизированная запись");
        // файлы
        Path src1 = Files.createTempFile("src1", ".txt");
        Path src2 = Files.createTempFile("src2", ".txt");
        Path target = Files.createTempFile("target", ".txt");

        // файлы-источники
        Files.write(src1, Arrays.asList(
                "файл1-строка1\n",
                "файл1-строка2\n",
                "файл1-строка3"
        ));
        System.out.println("> Файл 1:");
        System.out.println("-------");
        Files.readAllLines(src1).forEach(System.out::println);
        System.out.println("-------");

        Files.write(src2, Arrays.asList(
                "файл2-строка1\n",
                "файл2-строка2"
        ));
        System.out.println("> Файл 2:");
        System.out.println("-------");
        Files.readAllLines(src2).forEach(System.out::println);
        System.out.println("-------");

        System.out.println();

        // целевой файл
        Files.write(target, new byte[0]);

        // потоки
        LineWriterNoSync lineWriterNoSync = new LineWriterNoSync();
        
        // создание
        Thread thread1 = new Thread(() -> {
            try {
                System.out.println(">> поток 1 начат");
                lineWriterNoSync.appendToFile(src1.toString(), target.toString());
                System.out.println(">> поток 1 завершен");
            }catch (IOException e){
                System.err.println(">> Ошибка потока 1" + e);
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                System.out.println(">> поток 2 начат");
                lineWriterNoSync.appendToFile(src2.toString(), target.toString());
                System.out.println(">> поток 2 завершен");
            }catch (IOException e){
                System.err.println(">> Ошибка потока 2" + e);
            }
        });

        // выполнение
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        
        // результат
        System.out.println("> Целевой файл:");
        System.out.println("-------");
        Files.readAllLines(target).forEach(System.out::println);
        System.out.println("-------");
        
        // чистка
        Files.deleteIfExists(src1);
        Files.deleteIfExists(src2);
        Files.deleteIfExists(target);
    }

    /**
     * Синхронизированная запись
     * менее хрупко
     */
    public static void writeSync() throws Exception {
        System.out.println("Синхронизированная запись");
        // файлы
        Path src1 = Files.createTempFile("src1", ".txt");
        Path src2 = Files.createTempFile("src2", ".txt");
        Path target = Files.createTempFile("target", ".txt");

        // файлы-источники
        Files.write(src1, Arrays.asList(
                "файл1-строка1\n",
                "файл1-строка2\n",
                "файл1-строка3"
        ));
        System.out.println("> Файл 1:");
        System.out.println("-------");
        Files.readAllLines(src1).forEach(System.out::println);
        System.out.println("-------");

        Files.write(src2, Arrays.asList(
                "файл2-строка1\n",
                "файл2-строка2"
        ));
        System.out.println("> Файл 2:");
        System.out.println("-------");
        Files.readAllLines(src2).forEach(System.out::println);
        System.out.println("-------");

        System.out.println();

        // целевой файл
        Files.write(target, new byte[0]);

        // потоки
        LineWriterSync lineWriterSync = new LineWriterSync();
        
        // создание
        Thread thread1 = new Thread(() -> {
            try {
                System.out.println(">> поток 1 начат");
                lineWriterSync.appendToFile(src1.toString(), target.toString());
                System.out.println(">> поток 1 завершен");
            }catch (IOException e){
                System.err.println(">> Ошибка потока 1" + e);
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                System.out.println(">> поток 2 начат");
                lineWriterSync.appendToFile(src2.toString(), target.toString());
                System.out.println(">> поток 2 завершен");
            }catch (IOException e){
                System.err.println(">> Ошибка потока 2" + e);
            }
        });

        // выполнение
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        
        // результат
        System.out.println("> Целевой файл:");
        System.out.println("-------");
        Files.readAllLines(target).forEach(System.out::println);
        System.out.println("-------");
        
        // чистка
        Files.deleteIfExists(src1);
        Files.deleteIfExists(src2);
        Files.deleteIfExists(target);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Демо хрупкого поведения");
        
        RaceCondition.writeNoSync();
        RaceCondition.writeSync();
        
    }
}
