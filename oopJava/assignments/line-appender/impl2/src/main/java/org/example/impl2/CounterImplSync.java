package org.example.impl2;

import org.example.api.Counter;

/**
 * инкремент синхронизирован
 */
public final class CounterImplSync implements Counter {
    private int counter = 0;

    @Override
    public synchronized void increment() {
        counter++;
    }

    @Override
    public int getValue() {
        return counter;
    }
}
