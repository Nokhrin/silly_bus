package DemoTyping.MultipleInheritance;

public interface Operation {
    void perform();
    double getAmount();
    String getId();
}

/*
Есть у тебя две операции
Пополнения - Deposit
Снятия - Withdraw

Обе операции представленны разными типами
Формально... interface

Главный момент, являются ли эти типы взаимо исключающими? Являються ли interface взаимо исключающими?

И что надо сделать с типами, что бы они были взаимо исключающими?
 */