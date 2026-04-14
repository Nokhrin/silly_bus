package com.parser.core;

import java.util.Optional;

/**
 * Контейнер "или-или".
 * Реализует грамматику `A | B`
 */
public interface Either<A, B> {

    Optional<A> getLeft();
    Optional<B> getRight();

    /**
     * Создает контейнер с левым аргументом.
     *
     * @param a
     * @param <A>
     * @param <B>
     * @return
     */
    static <A, B> Either<A, B> left(A a) {
        return new Either<A, B>() {
            private final Optional<A> left = Optional.of(a);

            public Optional<A> getLeft() {
                return left;
            }

            public Optional<B> getRight() {
                return Optional.empty();
            }
        };
    }

    /**
     * Создает контейнер с правым аргументом.
     *
     * @param b
     * @param <A>
     * @param <B>
     * @return
     */
    static <A, B> Either<A, B> right(B b) {
        return new Either<A, B>() {
            private final Optional<B> right = Optional.of(b);

            public Optional<A> getLeft() {
                return Optional.empty();
            }

            public Optional<B> getRight() {
                return right;
            }
        };
    }
}
