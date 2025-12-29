package command_parser;

import account_system.Account;
import account_system.AccountService;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Команда
 * Выполняется
 * 
 * Требования:
 *     команды реализуют интерфейс Command
 *     команда - это record (или class), содержащий необходимые данные (например, Account, BigDecimal)
 *     команды неизменяемые (immutable) 
 * 
 * Команды - это инструкции, которые знают, какие действия нужно выполнить
 * Реализация действий делегирована системе управления счетами account_system
 * 
 * open                                          -> создать новый счет (возвращает номер)
 * close <номер_счета>                           -> закрыть счет
 * deposit <номер_счета> <сумма>                 -> пополнить счет
 * withdraw <номер_счета> <сумма>                -> снять со счета
 * transfer <номер_счета> <номер_счета> <сумма>  -> перевести со счета на счет
 * balance <номер_счета>                         -> посмотреть баланс
 * list                                          -> вывести список всех открытых счетов
 */
sealed interface Command permits Balance, Close, Deposit, List, Open, Transfer, Withdraw {
    void execute();
}



/**
 * создать новый счет (возвращает номер)
 */
record Open() implements Command {
    @Override
    public void execute() {
        System.out.printf("""
                Открытие счета:
                 открыт счет=%s
                %n""");
        ;
    }
    
}

/**
 * закрыть счет
 */
record Close() implements Command {

    @Override
    public void execute() {
        System.out.printf("""
                Закрытие счета:
                закрываемый счет=%s
                %n""");
    }
}

/**
 * пополнить счет
 */
record Deposit() implements Command {

    @Override
    public void execute() {
        System.out.printf("""
                Зачисление:
                  сумма=%s
                , счет зачисления=%s
                %n""");
    }
}

/**
 * снять со счета
 */
record Withdraw() implements Command {

    @Override
    public void execute() {
        System.out.printf("""
                Снятие:
                  сумма=%s
                , счет снятия=%s
                %n""");
    }
}

/**
 * перевести со счета на счет
 */
record Transfer() implements Command {

    @Override
    public void execute() {
        System.out.printf("""
                Перевод
                  сумма=%s
                , счет отправителя=%s
                , счет получателя=%s
                %n""");
    }
}

/**
 * посмотреть баланс
 */
record Balance() implements Command {

    @Override
    public void execute() {
        System.out.printf("""
                Баланс
                  счет=%s
                , сумма=%s
                %n""");
    }
}

/**
 * вывести список всех открытых счетов
 * требуется доступ к реестру счетов
 */
record List() implements Command {

    @Override
    public void execute() {
        System.out.printf("""
                Существуют открытые счета ...
                %n""");
    }
}



// реализация позже

//
///**
// * создать новый счет (возвращает номер)
// */
//record Open(AccountService accountService) implements Command {
//    @Override
//    public void execute() {
//        UUID accountId = accountService.openAccount(BigDecimal.ZERO);
//        System.out.printf("""
//                Открытие счета:
//                 открыт счет=%s
//                %n""", accountId);
//        ;
//    }
//
//}
//
///**
// * закрыть счет
// */
//record Close(Account account) implements Command {
//
//    @Override
//    public void execute() {
//        System.out.printf("""
//                Закрытие счета:
//                закрываемый счет=%s
//                %n""", account);
//    }
//}
//
///**
// * пополнить счет
// */
//record Deposit(BigDecimal amount, Account account) implements Command {
//
//    @Override
//    public void execute() {
//        System.out.printf("""
//                Зачисление:
//                  сумма=%s
//                , счет зачисления=%s
//                %n""", amount, account);
//    }
//}
//
///**
// * снять со счета
// */
//record Withdraw(BigDecimal amount, Account account) implements Command {
//
//    @Override
//    public void execute() {
//        System.out.printf("""
//                Снятие:
//                  сумма=%s
//                , счет снятия=%s
//                %n""", amount, account);
//    }
//}
//
///**
// * перевести со счета на счет
// */
//record Transfer(BigDecimal amount, Account accountSource, Account accountTarget) implements Command {
//
//    @Override
//    public void execute() {
//        System.out.printf("""
//                Перевод
//                  сумма=%s
//                , счет отправителя=%s
//                , счет получателя=%s
//                %n""", amount, accountSource, accountTarget);
//    }
//}
//
///**
// * посмотреть баланс
// */
//record Balance(Account account) implements Command {
//
//    @Override
//    public void execute() {
//        System.out.printf("""
//                Баланс
//                  счет=%s
//                , сумма=%s
//                %n""", account, account.getBalance());
//    }
//}
//
///**
// * вывести список всех открытых счетов
// * требуется доступ к реестру счетов
// */
//record List() implements Command {
//
//    @Override
//    public void execute() {
//        System.out.printf("""
//                Существуют открытые счета ...
//                %n""");
//    }
//}
