package org.example.abstraction;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileOperations {
    private static RandomAccessFile file;
    private static int byteFromFile;
    private static Path tempFile;
    
    public static void readSuccess() throws IOException {
        // тестовый файл
        tempFile = Files.createTempFile("fragile_", ".txt");
        Files.writeString(tempFile, "хрупкое поведение при использовании RandomAccessFile");

        // открытие файла -> чтение файла -> закрытие файла => happy path
        file = new RandomAccessFile(tempFile.toFile(), "r");
        byteFromFile = file.read();
        System.out.println("Успешно прочитан первый байт: " + byteFromFile);
        file.close();

    }

    public static void readFailure() throws IOException {
        // тестовый файл
        tempFile = Files.createTempFile("fragile_", ".txt");
        Files.writeString(tempFile, "хрупкое поведение при использовании RandomAccessFile");
        
        // открытие файла -> закрытие файла -> чтение файла => ошибка чтения
        file = new RandomAccessFile(tempFile.toFile(), "r");
        file.close();
        // ошибка Exception in thread "main" java.io.IOException: Stream Closed
//        firstByte = file.read();
    }


    /**
     * ошибка позиционирования курсора => выход за пределы файла
     * @throws IOException
     */
    public static void seekError() throws IOException {
        // тестовый файл
        tempFile = Files.createTempFile("fragile_", ".txt");
        Files.writeString(tempFile, "хрупкое поведение при использовании RandomAccessFile");
        
        // ожидаем n байт
        int n = 28;
        byte[] buffer = new byte[n];

        // на вход передан файл с n байтами - успех
        Files.writeString(tempFile, "казнить нельзя");
        file = new RandomAccessFile(tempFile.toFile(), "r");

        for (int i=0; i< n; i++){
            byteFromFile = file.read(); // read сдвигает указатель на +1
            buffer[i] = (byte) byteFromFile;
        }
        System.out.println(new String(buffer, 0, n - 1, StandardCharsets.UTF_8));
        //казнить нельзя

        // на вход передан файл с <n байтами - провал
        Files.writeString(tempFile, "казнить");
        file = new RandomAccessFile(tempFile.toFile(), "r");

        for (int i=0; i< n; i++){
            byteFromFile = file.read(); // read сдвигает указатель на +1
            buffer[i] = (byte) byteFromFile;
        }
        System.out.println(new String(buffer, 0, n - 1, StandardCharsets.UTF_8));
        //казнить�������������
    }

    /**
     * создано и не закрыто N файловых дескрипторов => утечка ресурсов
     * @throws IOException
     */
    public static void leftOpened() throws IOException {
        // тестовый файл
        tempFile = Files.createTempFile("fragile_", ".txt");
        Files.writeString(tempFile, "хрупкое поведение при использовании RandomAccessFile");

        for (int i = 0; i < 10000; i++){
            RandomAccessFile randomAccessFile = new RandomAccessFile(tempFile.toFile(), "r");
            System.out.println("Занятый адрес в памяти: " + randomAccessFile);
            System.out.println("Занятое пространство в файловой системе: " + tempFile.toFile().getAbsolutePath());
//            randomAccessFile.close();

            // результат:
            //Занятый адрес в памяти: java.io.RandomAccessFile@382c90c2
            //Занятое пространство в файловой системе: /tmp/fragile_7785392154943762929.txt
            //Занятый адрес в памяти: java.io.RandomAccessFile@859ea42
            //Занятое пространство в файловой системе: /tmp/fragile_7785392154943762929.txt
            //Занятый адрес в памяти: java.io.RandomAccessFile@28737371
            //...
            //Занятое пространство в файловой системе: /tmp/fragile_7785392154943762929.txt
            //Занятый адрес в памяти: java.io.RandomAccessFile@2af46afd
            //Занятое пространство в файловой системе: /tmp/fragile_7785392154943762929.txt
            //Занятый адрес в памяти: java.io.RandomAccessFile@760245e1
            //Занятое пространство в файловой системе: /tmp/fragile_7785392154943762929.txt
        }
    }

    /**
     * повышение надежности с помощью расширения поведения класса
     * @throws IOException
     */
    public static void safelyClosed() throws IOException {
        // тестовый файл
        tempFile = Files.createTempFile("fragile_", ".txt");
        Files.writeString(tempFile, "хрупкое поведение при использовании RandomAccessFile");

        for (int i = 0; i < 10000; i++){
            try (IFileResource randomAccessFile = new RandomAccessFileResource(tempFile)) {
                System.out.println("Занятый адрес в памяти: " + randomAccessFile);
                //Занятый адрес в памяти: org.example.abstraction.RandomAccessFileResource@45c2e0a6
                //Занятый адрес в памяти: org.example.abstraction.RandomAccessFileResource@119c745c
                //...
                //Занятый адрес в памяти: org.example.abstraction.RandomAccessFileResource@a7ad6e5
                //Занятый адрес в памяти: org.example.abstraction.RandomAccessFileResource@3b1ed14b
            } catch (IOException e) {
                System.err.println("Ошибка при работе с ресурсом: " + e.getMessage());
            }
            // закрытие файла
//            System.out.println("Занятый адрес в памяти: " + randomAccessFile); // ссылка не существует
        }
    }

    public static void main(String[] args) throws IOException {
//        FileOperations.readSuccess();
//        FileOperations.readFailure();
//        FileOperations.seekError();
//        FileOperations.leftOpened();
        FileOperations.safelyClosed();
    }
}