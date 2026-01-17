package account.system.cli;

import account.system.AccountService;
import account.system.Amount;

import java.math.BigDecimal;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        // создание сервиса
        AccountService service = new AccountService();
        System.out.println(service);
        // account.system.AccountService@5caf905d
        
        // создание счета с начальным балансом
        UUID accountId = service.openAccount(new Amount("1000"));
        System.out.println(accountId);
        // 5a3224cc-86a2-45a9-8fa8-b0d844b6b8b0
        
        // проверка баланса
        BigDecimal balance = service.getBalance(accountId);
        
        // сущность баланса отличается от сущности amount,
        // так как может быть отрицательной
        // сущность баланса близка по смыслу к типу BigDecimal
        // вопрос - 
        // использовать BigDecimal 
        //  или создать кастомный тип (по-моему, избыточно) ?
        System.out.println("баланс=" + balance);
        // баланс=1000

        // пополнение счета
        service.deposit(accountId, new Amount("500.05"));
        // проверка баланса
        System.out.println("баланс=" + service.getBalance(accountId));
        // баланс=1500.05

        // снятие
        service.withdraw(accountId, new Amount("123.45"));
        // проверка баланса
        System.out.println("баланс=" + service.getBalance(accountId));
        // баланс=1376.60
        
        // создание 2го счета
        UUID targetAccId = service.openAccount(new Amount("500"));
        System.out.println(targetAccId);
        // b81af15b-a0ea-455e-ab7a-44a1b1157a0c
        // проверка баланса
        System.out.println("баланс=" + service.getBalance(targetAccId));
        // баланс=500

        // перевод
        service.transfer(accountId, targetAccId, new Amount("1000"));
        // проверка балансов
        System.out.println("счет списания=" + accountId + " баланс=" + service.getBalance(accountId));
        // счет списания=1b53ee98-0099-4f3a-ba43-9df33c038e8a баланс=376.60
        System.out.println("счет зачисления=" + targetAccId + " баланс=" + service.getBalance(targetAccId));
        // счет зачисления=5f949a80-9709-4e0a-96c8-b2b15b400c3c баланс=1500

    }
}
