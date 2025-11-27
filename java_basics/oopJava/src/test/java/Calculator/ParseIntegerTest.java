package Calculator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * на вход 
 * "123 123"
 * offset = 0
 *
 * результат 
 * Optional.of(
 *   Result.of(
 *      value = 123
 *      nextOffset = 3
 *   ))
 *   
 *   ---
 *   
 *   на вход 
 * "x123 123"
 * offset = 0
 *
 * результат 
 * Optional.empty
 * 
 * ---
 * 
 * на вход 
 * "123 123"
 * offset = 2
 *
 * результат 
 * Optional.of(
 *   Result.of(
 *      value = 3
 *      nextOffset = 3
 *   ))
 *   
 *   ---
 *   
 *   
 *   
 *   плюс тесты которые сочтешь нужные
 */
class ParseIntegerTest {
    public static void main(String[] args) {
        assertTrue(false);
    }
}