package com.parser.core;

import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

public class EitherTest {
    @Test(description = "Хранит только левое значение")
    public void testEither_leftOnly() {
        Either<String, Optional> either = Either.left("123");

        assertEquals(either.getLeft().get(), "123");
        assertFalse(either.getRight().isPresent());
    }

    @Test(description = "Хранит только правое значение")
    public void testEither_rightOnly() {
        Either<Optional, String> either = Either.right("123");

        assertEquals(either.getRight().get(), "123");
        assertFalse(either.getLeft().isPresent());
    }

    @Test(description = "Контейнер не может не хранить значений")
    public void testEither_notEmpty() {
        Either<String, Optional> either = Either.left("123");
        boolean hasLeft = either.getLeft().isPresent();
        boolean hasRight = either.getRight().isPresent();

        assertTrue(hasLeft ^ hasRight);
    }

    @Test(description = "Контейнер не может хранить оба значения одновременно")
    public void testEither_exactlyOneValue() {
        Either<Optional, String> either = Either.right("123");
        boolean hasLeft = either.getLeft().isPresent();
        boolean hasRight = either.getRight().isPresent();

        assertFalse(hasLeft & hasRight);
    }

}