package pop.lesson03;

import java.util.HashMap;
import java.util.Map;


/**
 * Справочник «имя → возраст»
 */
public class Task2 {
    /**
     * Создать `Map<String, Integer>` для пар «имя → возраст».
     * Вариант на SortedMap - посчитал оправданным решением для задачи справочника
     */
    public static Map<String, Integer> mapNameToAgeSorted() {
        Map<String, Integer> nameToAge = new HashMap<>();
        // todo
        return nameToAge;
    }

    /**
     * Создать `Map<String, Integer>` для пар «имя → возраст».
     * Базовый вариант на HashMap
     */
    public static Map<String, Integer> mapNameToAgeFixed() {
        Map<String, Integer> nameToAge = new HashMap<>();

        nameToAge.put("Cóem", 1);
        nameToAge.put("Romey", 2);
        nameToAge.put("Sadhbh", 3);
        nameToAge.put("Kyro", 4);
        nameToAge.put("Paula", 5);

        return nameToAge;
    }
}
