package account.system.cli;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Ввод из файла.
 */
public final class FileInputSource implements InputSource {
    private final String filename;
    
    public FileInputSource(String filename) {
        this.filename=filename;
        
    }
    @Override
    public Scanner getScanner() {
        try {
            return new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Не найден файл: " + filename, e);
        }

    }

    @Override
    public void printPrompt() {
        
    }
}
