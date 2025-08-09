public class RemoveFirstAndLastLetter {
    /*
     * Task

Your goal is to write a function that removes the first and last characters of a string. You're given one parameter, the original string.

Important: Your function should handle strings of any length ≥ 2 characters. For strings with exactly 2 characters, return an empty string.

    Requirements

    The input string will always have at least 2 characters
    For strings with exactly 2 characters, return an empty string
    For strings with 3 or more characters, remove the first and last character
    The function should handle strings containing letters, numbers, and special characters
    */


    /* Строковые объекты в Java
     *
     * String - неизменяемые
     * StringBuffer - изменяемые, многопоточные
     * StringBuilder - изменяемые, однопоточные
     *
     * Сравнение строк
     * == - две строки являются один объектом
     * .equals() - проверка только значения, без проверки ссылки на объект
     */

    public static String remove(String str) {
        return (str.length() == 2) ? "" : (str.substring(1, str.length() - 1));
    }

   // assertions
    public void testFirstAndLastRemoved() {
        assert this.remove("eloquent").equals("loquen");
    }

    public void testShortestStringByReqs() {
        assert this.remove("QA").equals("");
    }

    public void testBoundaryFromShortestStringByReqs() {
        assert this.remove("FAQ").equals("A");
    }

    public static void main(String[] args) {
        RemoveFirstAndLastLetter rmfl = new RemoveFirstAndLastLetter();
        rmfl.testFirstAndLastRemoved();
        rmfl.testShortestStringByReqs();
        rmfl.testBoundaryFromShortestStringByReqs();
    }
}
