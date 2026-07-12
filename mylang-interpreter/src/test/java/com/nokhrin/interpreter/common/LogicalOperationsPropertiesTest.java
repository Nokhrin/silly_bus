package com.nokhrin.interpreter.common;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

import static org.testng.Assert.assertEquals;

public class LogicalOperationsPropertiesTest {
    /**
     * and(a, a) == a
     */
    @Property
    void idempotence_and(@ForAll boolean a) {
        ExprValue aBool = new BoolValue(a);
        assertEquals(aBool, LogicalOperations.and(aBool, aBool));
    }

    /**
     * or(a, a) == a
     */
    @Property
    void idempotence_or(@ForAll boolean a) {
        ExprValue aBool = new BoolValue(a);
        assertEquals(aBool, LogicalOperations.or(aBool, aBool));
    }

    /**
     * not(and(a, b)) == or(not(a), not(b))
     */
    @Property
    void deMorgan_law(
            @ForAll boolean a,
            @ForAll boolean b
    ) {
        ExprValue aBool = new BoolValue(a);
        ExprValue bBool = new BoolValue(b);
        assertEquals(
                LogicalOperations.not(LogicalOperations.and(aBool, bBool)),
                LogicalOperations.or(LogicalOperations.not(aBool), LogicalOperations.not(bBool))
        );
    }

    /**
     * not(not(a)) == a
     */
    @Property
    void double_neg(@ForAll boolean a) {
        ExprValue aBool = new BoolValue(a);
        assertEquals(aBool,
                LogicalOperations.not(LogicalOperations.not(aBool))
        );
    }

    /**
     * and(a, b) == and(b, a)
     */
    @Property
    void commutativity(
            @ForAll boolean a,
            @ForAll boolean b
    ) {
        ExprValue aBool = new BoolValue(a);
        ExprValue bBool = new BoolValue(b);
        assertEquals(
                LogicalOperations.and(aBool, bBool),
                LogicalOperations.and(bBool, aBool)
        );
    }

    /**
     * and(a, or(a, b)) == a
     */
    @Property
    void absorption(
            @ForAll boolean a,
            @ForAll boolean b
    ) {
        ExprValue aBool = new BoolValue(a);
        ExprValue bBool = new BoolValue(b);
        assertEquals(
                aBool,
                LogicalOperations.and(
                        aBool,
                        LogicalOperations.or(aBool, bBool)
                ));
    }

}