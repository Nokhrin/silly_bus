package org.example.impl2;

import org.example.api.LineWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class LineWriterSync implements LineWriter {
    @Override
    public synchronized void appendToFile(String sourceFile, String targetFile){
        
        
        // чтение исходника - все строки
        List<String> srcLines = null;
        try {
            srcLines = Files.readAllLines(Path.of(sourceFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // обработка прочитанных строк
        // source_file_name:src_line_num src_line_text nl
        int i = 1;
        for (String srcLine : srcLines) {
            // задержка 0-1 сек -> имитация затратной операции ввода-вывода
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // форматирование
            String formattedLine = sourceFile + ":" + i + " " + srcLine + "\n";

            // запись
            try {
                Files.write(Path.of(targetFile), formattedLine.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            i++;
        }
    }
}