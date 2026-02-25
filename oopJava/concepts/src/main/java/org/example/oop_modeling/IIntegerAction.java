package org.example.oop_modeling;

/**
 * функциональный интерфейс - интерфейс со строго одним методом
 * по примеру интерфейса Consumer
 */
    // типизация примитивом не удается - для обобщения требуется ссылочный тип
//interface IIntegerAction<int> {

    // лямбда - не Integer, такой интерфейс не выполнит выражение
//interface IIntegerAction<Integer> {
//    void perform(int n);
//}

    // лямбда требует параметр, интерфейс не принимает параметр => несоответствие сигнатур
//interface IIntegerAction {
//    void perform();
//}

interface IIntegerAction {
    /**
     * Вычислить выражение
     *
     * @param n
     * @return
     */
    int perform(int n);
}
