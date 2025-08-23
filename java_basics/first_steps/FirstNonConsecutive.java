/*
 * Задача
 * Дан массив целых чисел, упорядоченных по возрастанию
 * Каждое следующее число больше предыдущего на 1
 * В массиве может находиться число X, отличающееся от предыдущего более чем на 1
 * Вернуть значение X, если оно существует,
 * иначе - вернуть null
 * 
 * Гарантии
 * Массив содержит минимум два элемента
 * Все элементы - целые числа
 * Числа уникальны и расположены по возрастанию
 *
*/

public class FirstNonConsecutive {
    static Integer find(final int[] array) {
	for (int i = 1; i < array.length; i++) {
	    if (array[i] - array[i - 1] != 1) {
		return array[i];
	    }
	}
        return null;
    }

    public void testNoNonConsecutiveNumInArray() {
        assert this.find(new int[]{1, 2, 3, 4, 5, 6, 7, 8}) == null;
    }

    public void testNonConsecutiveNumInArrayStart() {
        assert this.find(new int[]{3, 5, 6, 7, 8, 9}) == Integer.valueOf(5);
    }

    public void testNonConsecutiveNumInArrayMiddle() {
        assert this.find(new int[]{1, 2, 3, 4, 6, 7, 8}) == Integer.valueOf(6); 
    }

    public void testNonConsecutiveNumInArrayEnd() {
        assert this.find(new int[]{6, 7, 8, 9, 11}) == Integer.valueOf(11);
    }

    public void testNonConsecutiveNumInArrayMinLength() {
        assert this.find(new int[]{2, 3}) == null;
    }

    public void testNonConsecutiveNumInArrayNegativesZeroPositive() {
        assert this.find(new int[]{-3, -2, 0, 1}) == 0;
    }

    public void testNonConsecutiveNumInArrayNegatives() {
        assert this.find(new int[]{-5, -4, -3, -1}) == -1;
    }

    public static void main(String[] args) {
        FirstNonConsecutive fnc = new FirstNonConsecutive();
        System.out.println("начал выполнение модульных тестов");

	fnc.testNoNonConsecutiveNumInArray();
	fnc.testNonConsecutiveNumInArrayStart();
	fnc.testNonConsecutiveNumInArrayMiddle();
	fnc.testNonConsecutiveNumInArrayEnd();
	fnc.testNonConsecutiveNumInArrayMinLength();
	fnc.testNonConsecutiveNumInArrayNegativesZeroPositive();
	fnc.testNonConsecutiveNumInArrayNegatives();
    }
}

/*
 * === мысли вслух
 *
 * до чего интересно!
 * задача вернуть int или null
 * определил тип возвращаемого значения как int
 * и получил ошибку при компиляции
 * > error: incompatible types: <null> cannot be converted to int
 *
 * в чем же дело?
 * и как мне решить задачку?
 * 
int is a primitive, null is not a value that it can take on. You could change the method return type to return java.lang.Integer and then you can return null, and existing code that returns int will get autoboxed.

Nulls are assigned only to reference types, it means the reference doesn't point to anything. Primitives are not reference types, they are values, so they are never set to null.

Using the object wrapper java.lang.Integer as the return value means you are passing back an Object and the object reference can be null.
 *
 *
 *
 *
 * ЗАРАБОТАЛО
 *
$ javac FirstNonConsecutive.java 
$ java -ea FirstNonConsecutive 

начал выполнение модульных тестов
null
Exception in thread "main" java.lang.AssertionError
        at FirstNonConsecutive.testNonConsecutiveNumInArray(FirstNonConsecutive.java:50)
        at FirstNonConsecutive.main(FirstNonConsecutive.java:59)
*/
