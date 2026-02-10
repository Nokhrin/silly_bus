package org.example.api;

//import org.example.impl1.UppercaseProcessor;
//
//public sealed interface Processor permits UppercaseProcessor {
//    String process(String input);
//}
//
// sealed (ошибка генерируется idea) требует объявления sealed интерфейса и его реализации в одном модуле
// permits + implements между модулями приводит к циклическому импорту

public interface Processor {
    String process(String input);
}
