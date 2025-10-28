```mermaid

---
title: Bank account management
---

classDiagram
    direction LR

    class Account{
        -final UUID id
        -BigInteger balance
        -List~UUID~ operations
        +getId() UUID
        +getBalance() BigInteger
        +getOperations() List~UUID~
        +deposit(balance)
        +withdraw(balance)
    }
    
    class Operation{
        %% абстрактный - так как существует только в контексте операций-наследников
        <<Abstract>>
        -final UUID id
        -BigInteger amount
        -Currency currencyCode
        -LocalDateTime completionTime
        +getId() UUID
        +perform(amount)
    }

    %% связь со счетом
    Account --> "many" Operation : contains

    %% Пополнение    
    Operation <|-- Deposit : implements
    Deposit : -Account targetAccount

    Account "1" o-- "0..*" Deposit : aggregation


    %% Снятие
    Operation <|-- Withdrawal : implements
    %% Баланс >= сумма снятия
    Withdrawal : -Account sourceAccount
    
    Account "1" o-- "0..*" Withdrawal : aggregation
    

    %% Перевод
    Operation <|-- Transfer : implements
    %% Баланс >= сумма снятия
    Transfer : -Account sourceAccount
    Transfer : -Account targetAccount
    
    Account "1" o-- "0..*" Transfer : aggregation

    
    namespace Operations {
        class Operation
        class Deposit
        class Transfer
        class Withdrawal
    }

```