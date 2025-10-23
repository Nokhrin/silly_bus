```mermaid

---
title: Счет / Account
---

stateDiagram-v2
    direction LR
    %%Created : создан, не активен
    %%Active : активен, баланс > 0, операции возможны
    %%Overdrawn : активен, баланс < 0, операции невозможны
    %%Blocked : счет заблокирован, операции невозможны
    %%Closed : закрыт

    [*] --> Created : создание
    Created --> Active : активация

    %% Операции

    %% Пополнение
    Active --> Deposit
    
    state if_blocked_Deposit <<choice>>
    state if_balance_positive_Deposit <<choice>>

    state Deposit {
        direction LR
        [*] --> if_blocked_Deposit : проверка наличия блокировки
        if_blocked_Deposit --> performDeposit : не заблокирован
        performDeposit --> updateBalanceDeposit
        updateBalanceDeposit --> DepositCompleted
        if_blocked_Deposit --> DepositCompleted : счет заблокирован
        DepositCompleted --> [*]
    }
    Deposit --> if_balance_positive_Deposit
    if_balance_positive_Deposit --> Active : if balance >= 0
    if_balance_positive_Deposit --> Overdrawn: if balance < 0


    %% Снятие
    Active --> Withdrawal
    
    state if_blocked_Withdrawal <<choice>>
    state Withdrawal {
        direction LR
        [*] --> if_blocked_Withdrawal : проверка наличия блокировки
        if_blocked_Withdrawal --> performWithdrawal : не заблокирован
        performWithdrawal --> updateBalanceWithdrawal
        updateBalanceWithdrawal --> WithdrawalCompleted
        if_blocked_Withdrawal --> WithdrawalCompleted : счет заблокирован
        WithdrawalCompleted --> [*]
    }

    state if_balance_positive_Withdrawal <<choice>>
    Withdrawal --> if_balance_positive_Withdrawal
    if_balance_positive_Withdrawal --> Active : if balance >= 0
    if_balance_positive_Withdrawal --> Overdrawn: if balance < 0


    %% Перевод
    Active --> Transfer

    state if_blocked_Transfer <<choice>>
    state if_valid_recipient <<choice>>
    state Transfer {
        direction LR
        [*] --> if_blocked_Transfer : проверка наличия блокировки
        if_blocked_Transfer --> getRecipient
        getRecipient --> if_valid_recipient
        if_valid_recipient --> performTransfer : получатель подтвержден
        performTransfer --> updateBalanceTransfer
        updateBalanceTransfer --> TransferCompleted
        
        if_valid_recipient --> TransferCompleted : получатель не подтвержден
        if_blocked_Transfer --> TransferCompleted : счет заблокирован
        TransferCompleted --> [*]
    }

    state if_balance_positive_Transfer <<choice>>
    Transfer --> if_balance_positive_Transfer
    if_balance_positive_Transfer --> Active : if balance >= 0
    if_balance_positive_Transfer --> Overdrawn: if balance < 0

    Active --> Blocked : наложение блокировки
    Blocked --> Active : снятие блокировки

    Overdrawn --> Active : пополнение до положительного баланса
    Overdrawn --> Blocked : наложение блокировки

    Created --> Closed : закрытие
    Active --> Closed : закрытие
    Overdrawn --> Closed : закрытие
    Blocked --> Closed : закрытие

    Closed --> [*] : операции невозможны

```