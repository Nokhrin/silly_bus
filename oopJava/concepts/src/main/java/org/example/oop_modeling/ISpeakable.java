package org.example.oop_modeling;

/**

 * функциональный интерфейс - интерфейс со строго одним методом
 */
// sealed не позволяет создать анонимный класс ?
//sealed interface ISpeakable permits LambdasBasics {
interface ISpeakable {
    void say();
}
