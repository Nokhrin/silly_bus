package org.example.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LineWriterSync {
    private static final Object lock = new Object();

    public void appendToFile(String sourceFile, String targetFile) throws IOException {
        synchronized (lock) {
            try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(sourceFile));
                 BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(targetFile), StandardOpenOption.APPEND)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                }
            }
        }
    }
}
