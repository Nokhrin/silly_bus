package lambda_parser;

/**
 * Контейнер либо, либо (Either)
 *     interface Either< А,В> {
 *     static < A,В> Either< А,В> left(A a, Class< В> b)
 *     static < A,В> Either< А,В> right (B b, Class< A > а)
 *     Optional< А> getLeft();
 *     Optional< В> getRight();
 *     }
 *     Контейнер хранит строго либо одно, либо другое значение.
 *     Контейнер не может не хранить значений
 *     Контейнер не может хранить оба значения одновременно
 */
public class Either {
}
