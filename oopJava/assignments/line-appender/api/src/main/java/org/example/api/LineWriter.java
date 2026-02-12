package org.example.api;

import java.io.IOException;

public interface LineWriter {
    void appendToFile(String sourceFile, String targetFile) throws IOException, InterruptedException;
}
