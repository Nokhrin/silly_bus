package pop.lesson01;

/**
 * Свойства строковых объектов и следствия для реализации
 * - строки неизменяемы
 * поэтому задача
 * "заменить символ в строке"
 * в реальности означает
 * "создать новую строку с использованием иного символа вместо заменяемого"
 *
 * отсюда следует неэффективность работы с памятью:
 *  для каждой создаваемой подстроки будет инициализирован новый объект
 *  в результате получим O(N) расход памяти, где N - количество символов в строке
 *  сложность по времени O(N) операций чтения и/или замены символа
 *      один посимвольный обход строки
 *
 * строка может быть предствлена массивом элементов типа `char`
 *  проще говоря - массивом букв, составляющих строку
 *  массив изменяем
 *  задача не требует изменения количества символов,
 *      следовательно, можем обойтись созданием `char[]` из строки
 *      что по расходу памяти можно считать O(1)
 *      по расходу времени O(N) операций чтения и/или замены символа,
 *          один посимвольный обход строки при создании массива
 *          один посимвольный обход строки при замене символов
 *          один посимвольный обход массива при формировании строки
 *              - вот этот момент - уточнить - как реализован toString?
 *              возможно, эффективнее, чем O(n)
 *      созданием строки из обработанного массива `char[]`
 *
 * - строковый буфер - StringBuilder
 *      - изменяемая коллекция символов
 *
 * - строковый буфер - StringBuffer
 *      - похож по свойствам на List<Character>
 *      - изменяем - позволяет изменить количество элементов
 *      - по заданию не требуется, но знать пригодится
 *
 *
 * полагаю, что строковый буфер можно использовать как динамическую строку
 */
public class StringReplace {
    /**
     * Создает новую строку с помощью метода объединения строк `+`
     * @param inputString - строка, в которой требуется заменить символы
     *                      может быть пустой
     *                      может представлять произвольную последовательность из символов `A`, `B`, `X`, `D`
     * @return строка, в которой все вхождения буквы `X` заменены буквой `C`
     */
    public static String replaceLetterPlus(String inputString) {
        String processedString = "";  // финальная строка
        // цикл будет выполнен только для непустой строки в `inputString`
        for (int i = 0; i < inputString.length(); i++) {
            /* инвариант
            length финальной строки равен i + 1
            */
            char letter = inputString.charAt(i);  // символ в пределах итерации
            if (letter == 'X') {
                processedString += "C";
            } else {
                processedString += Character.toString(letter);  // создается объект String для каждого символа
            }  // при каждом выполнении создается новый объект String для результирующей строки
        }
        return processedString;
    }

    /**
     * Создает новую строку с помощью нестатического метода String.concat()
     * @param inputString - строка, в которой требуется заменить символы
     *                      может быть пустой
     *                      может представлять произвольную последовательность из символов `A`, `B`, `X`, `D`
     * @return строка, в которой все вхождения буквы `X` заменены буквой `C`
     */
    public static String replaceLetterConcat(String inputString) {
        String processedString = "";  // финальная строка
        // цикл будет выполнен только для непустой строки в `inputString`
        for (int i = 0; i < inputString.length(); i++) {
            char letter = inputString.charAt(i);  // символ в пределах итерации
            if (letter == 'X') {
                processedString = processedString.concat("C");
            } else {
                processedString = processedString.concat(Character.toString(letter));  // создается объект String для каждого символа
            }  // при каждом выполнении создается новый объект String для результирующей строки
        }
        return processedString;
    }

    /**
     * Создает новую строку с помощью метода объединения символов массива char[]
     * @param inputString - строка, в которой требуется заменить символы
     *                      может быть пустой
     *                      может представлять произвольную последовательность из символов `A`, `B`, `X`, `D`
     * @return строка, в которой все вхождения буквы `X` заменены буквой `C`
     */
    public static String replaceLetterCharArray(String inputString) {
        char[] processed = inputString.toCharArray();
        for (int i = 0; i < processed.length; i++) {
            if (processed[i] == 'X') processed[i] = 'C';
        }
        return String.valueOf(processed);
    }

    /**
     * Создает новую строку с помощью StringBuilder
     * @param inputString - строка, в которой требуется заменить символы
     *                      может быть пустой
     *                      может представлять произвольную последовательность из символов `A`, `B`, `X`, `D`
     * @return строка, в которой все вхождения буквы `X` заменены буквой `C`
     */
    public static String replaceLetterStringBuilder(String inputString) {
        StringBuilder processed = new StringBuilder();
        for (int i = 0; i < inputString.length(); i++) {
            char letter = inputString.charAt(i);
            if (letter == 'X') {
                processed.append('C');
            } else {
                processed.append(letter);
            }
        }
        return processed.toString();
    }

    /**
     * Создает новую строку с помощью StringBuffer
     * @param inputString - строка, в которой требуется заменить символы
     *                      может быть пустой
     *                      может представлять произвольную последовательность из символов `A`, `B`, `X`, `D`
     * @return строка, в которой все вхождения буквы `X` заменены буквой `C`
     */
    public static String replaceLetterStringBuffer(String inputString) {
        StringBuffer processed = new StringBuffer();
        for (int i = 0; i < inputString.length(); i++) {
            char letter = inputString.charAt(i);
            switch (letter) {
                case 'X' -> processed.append("C");
                default -> processed.append(letter);
            }
        }
        return processed.toString();
    }

    /**
     * Измеряю время выполнения разных реализаций
     * Лидирует replaceLetterCharArray
     * @param args - не передается
     */
    public static void main(String[] args) {
        int count = 100_000;
        String stringToProcess = "X".repeat(count);
        long startTime, endTime;

        // 1
        startTime = System.currentTimeMillis();
        replaceLetterPlus(stringToProcess);
        endTime = System.currentTimeMillis();
        System.out.println("Метод с использованием +\n" + count + " замен выполнены в течение\n" + (endTime - startTime) + " миллисек\n");

        // 2
        startTime = System.currentTimeMillis();
        replaceLetterConcat(stringToProcess);
        endTime = System.currentTimeMillis();
        System.out.println("Метод с использованием String.concat()\n" + count + " замен выполнены в течение\n" + (endTime - startTime) + " миллисек\n");

        // 3
        startTime = System.currentTimeMillis();
        replaceLetterCharArray(stringToProcess);
        endTime = System.currentTimeMillis();
        System.out.println("Метод с использованием char[]\n" + count + " замен выполнены в течение\n" + (endTime - startTime) + " миллисек\n");

        // 4
        startTime = System.currentTimeMillis();
        replaceLetterStringBuilder(stringToProcess);
        endTime = System.currentTimeMillis();
        System.out.println("Метод с использованием StringBuilder\n" + count + " замен выполнены в течение\n" + (endTime - startTime) + " миллисек\n");

        // 5
        startTime = System.currentTimeMillis();
        replaceLetterStringBuffer(stringToProcess);
        endTime = System.currentTimeMillis();
        System.out.println("Метод с использованием StringBuffer\n" + count + " замен выполнены в течение\n" + (endTime - startTime) + " миллисек\n");

    }
}
