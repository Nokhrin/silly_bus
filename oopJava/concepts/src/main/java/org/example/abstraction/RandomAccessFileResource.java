package org.example.abstraction;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

public class RandomAccessFileResource  implements IFileResource{
    private RandomAccessFile randomAccessFile;

    public RandomAccessFileResource(Path filePath) throws IOException {
        randomAccessFile = new RandomAccessFile(filePath.toFile(), "r");
    }

    @Override
    public void close() throws IOException {
        if (randomAccessFile != null){
            randomAccessFile.close();
        }
    }
}
