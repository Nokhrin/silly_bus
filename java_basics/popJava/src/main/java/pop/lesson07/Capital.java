package pop.lesson07;

import org.apache.commons.lang3.StringUtils;

/**
 * Задача - применить библиотеку `commons-lang3`, добавленную в проект по заданию урока 7
 */
public class Capital {
    /**
     * Устанавливает в верхний регистр первую букву каждого слова переданной строки
     * @param str строковое значение, не изменяется, копируется
     * @return строка, в которой каждое слово начинается с буквы в верхнем регистре
     */
    public static String createCapitalizedString(String str) {
        return StringUtils.capitalize(str);
    }
}
