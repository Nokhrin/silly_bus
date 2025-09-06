package oop;

public class MainRunner {
    public static void main(String[] args) {

        Hero hero = new Hero();
        System.out.println(hero.name);
        Hero hero1 = new Hero("asdf");
        System.out.println(hero1.name);
    }
}
