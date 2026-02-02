package account.system.cli;

import account.system.AccountRepository;
import account.system.InMemoryAccountRepository;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        AccountRepository accountRepository = new InMemoryAccountRepository();
        
        InputSource inputSource = getInputSource(args);
        Scanner scanner = inputSource.getScanner();

        CommandLineProcessor commandLineProcessor = new CommandLineProcessor(scanner, accountRepository);
        commandLineProcessor.start();

    }

    private static InputSource getInputSource(String[] args) {
        if (args.length >= 2 && "--file".equalsIgnoreCase(args[0])) {
            return new FileInputSource(args[1]);
        }
        return new InteractiveInputSource();
    }
}


// Ручная проверка
// Интерактивный режим

// happy path
//
// открыть счет 1
// зачисление на счет 1 - 100
// снятие со счета 1 50
// открыть счет 2
// список счетов
// перевод со счета 1 на счет 2 - 20 
// баланс счета 2 - 20
// баланс счета 1 - 30
// закрытие счета 1
// список счетов
// завершить работу программы

// Управление банковскими счетами. Введите команду (или 'exit' для выхода):
// > open
// ok Успешно открыт счет beff0943-1dc5-41bd-86cf-13b8e6443e3b [состояние системы было изменено]
// > deposit beff0943-1dc5-41bd-86cf-13b8e6443e3b 100
// ok Успешно зачислено 100 на счет beff0943-1dc5-41bd-86cf-13b8e6443e3b [состояние системы было изменено]
// > withdraw beff0943-1dc5-41bd-86cf-13b8e6443e3b 50
// ok Успешно снято 50 со счета beff0943-1dc5-41bd-86cf-13b8e6443e3b [состояние системы было изменено]
// > open
// ok Успешно открыт счет 522e49cb-1c57-47f0-944e-d1e7d5ee7ce7 [состояние системы было изменено]
// > list
// ok В системе существуют счета: 522e49cb-1c57-47f0-944e-d1e7d5ee7ce7 beff0943-1dc5-41bd-86cf-13b8e6443e3b [состояние системы не было изменено]
// > transfer beff0943-1dc5-41bd-86cf-13b8e6443e3b 522e49cb-1c57-47f0-944e-d1e7d5ee7ce7 20
// ok Успешно выполнен перевод со счета на счет [состояние системы было изменено]
// > balance 522e49cb-1c57-47f0-944e-d1e7d5ee7ce7
// ok Баланс счета 522e49cb-1c57-47f0-944e-d1e7d5ee7ce7: 20.00 [состояние системы не было изменено]
// > balance beff0943-1dc5-41bd-86cf-13b8e6443e3b
// ok Баланс счета beff0943-1dc5-41bd-86cf-13b8e6443e3b: 30.00 [состояние системы не было изменено]
// > close beff0943-1dc5-41bd-86cf-13b8e6443e3b
// ok Успешно закрыт счет beff0943-1dc5-41bd-86cf-13b8e6443e3b [состояние системы было изменено]
// > list
// ok В системе существуют счета: 522e49cb-1c57-47f0-944e-d1e7d5ee7ce7 [состояние системы не было изменено]
// > exit
// Завершение работы


// негативы
//
// закрытие несуществующего счета - неудача
// снятие с несуществующего счета - неудача
// зачисление на несуществующий счет - неудача
// баланс несуществующего счета - неудача
// открыть счет - успех
// зачислить на счет -10, amount < 0 - неудача
// снятие с существующего счета 20, amount > balance - неудача
// перевод с существующего счета на несуществующий счет - получатель не найден
// завершить работу программы - успех

// Управление банковскими счетами. Введите команду (или 'exit' для выхода):
// > close beff0943-1dc5-41bd-86cf-13b8e6443e3b
// err Счет beff0943-1dc5-41bd-86cf-13b8e6443e3b не существует [состояние системы не было изменено до ошибки]
// > withdraw beff0943-1dc5-41bd-86cf-13b8e6443e3b 10
// err Счет не найден [состояние системы не было изменено до ошибки]
// > deposit beff0943-1dc5-41bd-86cf-13b8e6443e3b 10
// err Счет не найден [состояние системы не было изменено до ошибки]
// > balance beff0943-1dc5-41bd-86cf-13b8e6443e3b
// err Счет не найден [состояние системы не было изменено до ошибки]
// > open
// ok Успешно открыт счет 716d4fb7-c576-42b6-a675-3b199de6bbf5 [состояние системы было изменено]
// > deposit 716d4fb7-c576-42b6-a675-3b199de6bbf5 -10
// err Ошибка при зачислении: Сумма должна быть строго больше нуля [состояние системы не было изменено до ошибки]
// > withdraw 716d4fb7-c576-42b6-a675-3b199de6bbf5 1
// err Недостаточно средств для снятия [состояние системы не было изменено до ошибки]
// > transfer 716d4fb7-c576-42b6-a675-3b199de6bbf5 716d4fb7-c576-42b6-a675-3b199de6bbf3 1
// err Счет назначения не найден [состояние системы не было изменено до ошибки]
// > exit
// Завершение работы