package pop.lesson08;

import pop.lesson04.ScannerInput;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * todo
 *  передай текст аргументом, убери блокирующий сканер
 */
public class FileOps {
    public static Logger logger = Logger.getLogger(ScannerInput.class.getName());
    public static final String filePath = "target/out_file.txt";
    public static final String mapExportPath = "target/tasks.csv";

    public static void writeToFile() throws RuntimeException {
        Scanner sc = new Scanner(System.in);
        System.out.print("введи текст для записи в файл, нажми enter: ");
        System.out.flush();  // todo - надо ли? проверь
        String message = sc.nextLine();
        try (FileWriter fw = new FileWriter(filePath)) {
            fw.write(message);
            logger.log(Level.INFO, "данные успешно записаны в файл " + filePath);
        } catch (IOException e) {
            logger.log(Level.WARNING, "ошибка записи в файл\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void appendToFile(){
        Scanner sc = new Scanner(System.in);
        System.out.print("введи текст для добавления в файл, нажми enter: ");
        String message = sc.nextLine();
        try (FileWriter fw = new FileWriter(filePath, true)) {
            fw.write("\n" + message);
            logger.log(Level.INFO, "данные успешно добавлены в файл " + filePath);
        } catch (IOException e) {
            logger.log(Level.WARNING, "ошибка записи в файл\n" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Записывает пары ключ-значение в текстовый файл
     * @param mapToWrite
     */
    public static void writeMapToFile(SortedMap<String, Integer> mapToWrite) throws IOException {
        File file = new File(mapExportPath);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            /* запись мапы
            ключ,значение
            каждая пара на новой строке
            */
            for (Map.Entry<String, Integer> entry : mapToWrite.entrySet()) {
                bw.write(String.format("%s,%d", entry.getKey(), entry.getValue()));
                bw.newLine();
            }
        } catch (IOException ioe) {
            logger.log(Level.WARNING, ioe.getMessage());
        } finally {
            assert bw != null;
            bw.close();
        }
    }

    public static void main(String[] args) {
        SortedMap<String, Integer> map = new TreeMap<>();
        map.put("один", 1);
        map.put("два", 2);
        map.put("три", 3);
        try {
            writeMapToFile(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
