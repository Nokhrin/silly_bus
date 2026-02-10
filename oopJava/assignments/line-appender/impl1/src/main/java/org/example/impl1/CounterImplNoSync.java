package org.example.impl1;

import org.example.api.Counter;

/**
 * инкремент синхронизирован
 */

public final class CounterImplNoSync implements Counter {
    private int counter = 0;
    @Override
    public void increment() {
        counter++;
    }

    @Override
    public int getValue() {
        return counter;
    }
}
