package org.example.impl1;

import org.example.api.LineWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class LineWriterNoSync implements LineWriter {

    @Override
    public void appendToFile(String sourceFile, String targetFile) throws IOException, InterruptedException {
        // чтение исходника - все строки
        List<String> srcLines = Files.readAllLines(Path.of(sourceFile));

        // обработка прочитанных строк
        // source_file_name:src_line_num src_line_text nl
        int i = 1;
        for (String srcLine : srcLines) {
            // задержка 0-1 сек -> имитация затратной операции ввода-вывода
            Thread.sleep((long) (Math.random() * 1000));

            // форматирование
            String formattedLine = sourceFile + ":" + i + " " + srcLine + "\n";
            
            // запись
            Files.write(Path.of(targetFile), formattedLine.getBytes(), StandardOpenOption.APPEND);
            
            i++;
        }
    }
}
