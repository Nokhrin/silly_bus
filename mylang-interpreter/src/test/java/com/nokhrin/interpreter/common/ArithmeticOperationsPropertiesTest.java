package com.nokhrin.interpreter.common;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;

import static org.testng.Assert.assertEquals;

public class ArithmeticOperationsPropertiesTest {

    /**
     * add(a, b) == add(b, a)
     */
    @Property
    void arithmetic_commutative(
            @ForAll @IntRange(min = Integer.MIN_VALUE) long a,
            @ForAll @IntRange(min = Integer.MIN_VALUE) long b) {
        ExprValue aInt = new IntValue(a);
        ExprValue bInt = new IntValue(b);
        assertEquals(
                ArithmeticOperations.add(aInt, bInt),
                ArithmeticOperations.add(bInt, aInt)
        );
    }

    /**
     * add(a, 0) == a
     */
    @Property
    void arithmetic_identity(
            @ForAll @IntRange(min = Integer.MIN_VALUE) long a) {
        ExprValue aInt = new IntValue(a);
        assertEquals(ArithmeticOperations.add(aInt, new IntValue(0)), aInt);
    }

    /**
     * sub(a, a) == 0
     */
    @Property
    void arithmetic_inverse(
            @ForAll @IntRange(min = Integer.MIN_VALUE) long a) {
        ExprValue aInt = new IntValue(a);
        assertEquals(ArithmeticOperations.sub(aInt, aInt), new IntValue(0));
    }

    /**
     * add(add(a, b), c) == add(a, add(b, c))
     * Только для `IntValue`
     */
    @Property
    void arithmetic_associative(
            @ForAll @IntRange(min = Integer.MIN_VALUE) long a,
            @ForAll @IntRange(min = Integer.MIN_VALUE) long b,
            @ForAll @IntRange(min = Integer.MIN_VALUE) long c
    ) {
        ExprValue aInt = new IntValue(a);
        ExprValue bInt = new IntValue(b);
        ExprValue cInt = new IntValue(c);
        assertEquals(
                ArithmeticOperations.add(ArithmeticOperations.add(aInt, bInt), cInt),
                ArithmeticOperations.add(aInt, ArithmeticOperations.add(bInt, cInt))
        );
    }

    /**
     * mul(a, add(b, c)) == add(mul(a, b), mul(a, c))
     */
    @Property
    void arithmetic_distributive(
            @ForAll @IntRange(min = Integer.MIN_VALUE) long a,
            @ForAll @IntRange(min = Integer.MIN_VALUE) long b,
            @ForAll @IntRange(min = Integer.MIN_VALUE) long c
    ) {
        ExprValue aInt = new IntValue(a);
        ExprValue bInt = new IntValue(b);
        ExprValue cInt = new IntValue(c);
        assertEquals(
                ArithmeticOperations.mul(aInt, ArithmeticOperations.add(bInt, cInt)),
                ArithmeticOperations.add(
                        ArithmeticOperations.mul(aInt, bInt),
                        ArithmeticOperations.mul(aInt, cInt)
                )
        );
    }

}