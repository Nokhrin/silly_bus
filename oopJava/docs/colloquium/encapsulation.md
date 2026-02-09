# Инкапсуляция, private, protected... какая польза и как тестировать инкапсулированное поведение

инкапсуляция - гарантия соблюдения требований к состоянию и поведению


```java

class Scratch {
    /**
     * класс `Rational` (рациональная дробь) с инвариантом:
     * - Знаменатель всегда > 0
     * - Дробь всегда приведена к несократимому виду (НОД(числитель, знаменатель) = 1)
     *
     * Требования:
     * - Нет публичных сеттеров
     * - Все операции (`add`, `multiply`) сохраняют инвариант
     * @param numerator
     * @param denominator
     */
    record Rational(int numerator, int denominator) {
        
        public Rational{
            // Знаменатель всегда > 0
            if (denominator == 0 ) {
                throw new IllegalArgumentException("Знаменатель не может быть равен нулю");
            }
            
            // Дробь всегда приведена к несократимому виду (НОД(числитель, знаменатель) = 1)
            int gcd = gcd(Math.abs(numerator), Math.abs(denominator));
            
            if (denominator < 0) {
                numerator = -numerator;
                denominator = -denominator;
            }
        }

        // НОД
        private static int gcd(int a, int b) {
            while (b != 0) {
                int temp = b;
                b = a % b;
                a = temp;
            }
            return a;
        }

        Rational add(Rational otherRational) {
            int newNumerator = this.numerator * otherRational.denominator + otherRational.numerator * this.denominator;
            int newDenominator = this.denominator * otherRational.denominator;
            return new Rational(newNumerator, newDenominator);
        } 
        Rational mult(Rational otherRational) {
            int newNumerator = this.numerator * otherRational.numerator;
            int newDenominator = this.denominator * otherRational.denominator;
            return new Rational(newNumerator, newDenominator);
        } 
    }
    public static void main(String[] args) {
        Rational r1 = new Rational(1, 2);
        Rational r2 = new Rational(1, 3);
        
        Rational sum = r1.add(r2);
        System.out.println(r1 + " + " + r2 + " -> " + sum);
        // Rational[numerator=1, denominator=2] + Rational[numerator=1, denominator=3] -> Rational[numerator=5, denominator=6]
        
        Rational mult = r1.mult(r2);
        System.out.println(r1 + " * " + r2 + " -> " + mult);
        // Rational[numerator=1, denominator=2] * Rational[numerator=1, denominator=3] -> Rational[numerator=1, denominator=6]
    }
}
```