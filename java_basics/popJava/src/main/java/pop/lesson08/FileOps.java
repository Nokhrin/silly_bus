package pop.lesson08;

import pop.lesson04.ScannerInput;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * todo
 *  передай текст аргументом, убери блокирующий сканер
 */
public class FileOps {
    public static final Logger logger = Logger.getLogger(ScannerInput.class.getName());

    /**
     * Записывает пары ключ-значение в текстовый файл
     * @param mapToWrite
     */
    public static void writeMapToFile(Map<String, Integer> mapToWrite, String filePath) throws IOException {
        File file = new File(filePath);
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
        } catch (IOException e) {
            logger.log(Level.SEVERE, "ошибка записи файла", e);
        } finally {
            assert bw != null;
            bw.close();
        }
    }

    /**
     * Считывает пары ключ-значение из текстового файла, создает и возвращает Map
     * Реализация на BufferedReader
     * @param filePath путь к файлу от корня проекта
     * @return Map ключ-значение, сохраняет порядок, содержащийся в файле
     * @throws IOException
     */
    public static Map<String, Integer> readMapFromFile(String filePath) throws IOException {
        // создаю мэп класса LinkedHashMap, так как он сохраняет порядок добавления элементов
        Map<String, Integer> mapRestored = new LinkedHashMap<>();

        /* читаю файл построчно
            намеренно не загружаю весь файл в память,
            так как объем файла заранее не определен, и содержимое может быть технически некорректным
        */
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "ошибка записи файла", e);
            throw new RuntimeException(e);
        }
        String line;
        String[] keyValue;

        // создаю отображение / Map
        while ((line = br.readLine()) != null) {
            keyValue = line.split(",");
            mapRestored.put(keyValue[0], Integer.valueOf(keyValue[1]));
        }

        return mapRestored;
    }

    public static void main(String[] args) throws IOException {
        Map<String, Integer> map = new TreeMap<>();
        map.put("один", 1);
        map.put("два", 2);
        map.put("три", 3);
        writeMapToFile(map, "target/map_export.txt");

        map = null;

        // считываю из файла
        try {
            map = readMapFromFile("target/tasks.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.log(Level.INFO, String.valueOf(map));

    }
}
