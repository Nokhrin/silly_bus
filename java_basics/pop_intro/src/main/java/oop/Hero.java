/**
 * Чему я научился*
 * - перегрузка конструктора - как способ установить для параметра значение по умолчанию
 */

package oop;

public class Hero {
    public String name;
    public String position = "00";
    public int health = 100;
    public int damage = 5;
    public int experience = 0;

    // constructor
    public Hero(String name) {
        this.name = name;
    }

    public Hero() {
        this("Hero");
    }
}
