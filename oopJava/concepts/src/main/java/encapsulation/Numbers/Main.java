package encapsulation.Numbers;

/*

нет состояния (полей) которое изменялось бы, да так изменялось бы, что приводило бы к некорректному поведению


 */

public class Main {
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
