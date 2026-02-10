package org.example.api;


import org.example.impl1.CounterImplNoSync;
import org.example.impl2.CounterImplSync;

sealed public interface Counter permits CounterImplSync, CounterImplNoSync {
    void increment();
    int getValue();
}
