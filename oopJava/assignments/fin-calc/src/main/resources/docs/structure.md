# Структура приложения

## Порядок событий

1. точка входа
2. создание менеджера счетов
3. создание исполнителя команд
4. парсинг ввода
5. создание операций
6. выполнение операций
7. завершение работы


## Жизненный цикл информации
1. парсер читает строку, возвращает List<Command>, где Command - dto - объект, содержащий условное обозначение операции + данные для выполнения операции
2. List<Commands> передается CommandExecutor
3. CommandExecutor передает последовательно Command из List<Command> классу, создающему экземпляры команд
4. Созданная команда выполняется

### Пример выполнения

ввод
```
open deposit 123e4567-e89b-12d3-a456-426614174000 100.00"
```

1. Парсер -> [OpenAccountData(), DepositData(accountId, amount)]
2. CommandExecutor.executeCommand() для каждого Command в List<Command> -> CommandFactory.createOperation()
3. CommandFactory -> List<Operation> = [OpenAccount(), Deposit(accountId, amount)]
4. Operation.execute() для каждой Operation в List<Operation>