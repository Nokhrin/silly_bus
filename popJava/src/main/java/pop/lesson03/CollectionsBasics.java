package pop.lesson03;

import java.util.SortedMap;
import java.util.TreeMap;


/**
 * Справочник «имя → возраст»
 */
public class CollectionsBasics {
    /**
     * Возвращает словарь для пар "строка: целое число"
     * Сортировка по умолчанию по возрастанию строки
     */
    public static SortedMap<String, Integer> createSortedMap(String strValue, int intValue) {
        SortedMap<String, Integer> sortedMap = new TreeMap<>();

        sortedMap.put(strValue, intValue);

        return sortedMap;
    }

    /**
     * Создать `Map<String, Integer>` для пар «имя → возраст».
     */
    public static void main(String[] args) {
        String name = "userName";
        int age = 42;
        SortedMap<String, Integer> nameToAge = createSortedMap(name, age);
        System.out.println(nameToAge);
    }
}
