# Формальная грамматика команд консольного приложения для управления банковскими счетами

```ebnf
command ::= open-account
          | close-account <account_id>
          | deposit <account_id> <amount>
          | withdraw <account_id> <amount>
          | transfer <account_id> <account_id> <amount>
          | balance <account_id>
          | list-accounts

account_id ::= digit { digit }
amount ::= [ '-' ] ( digit { digit } | digit { digit } '.' digit { digit } | '.' digit { digit } )
           | digit { digit } '.' digit { digit }

digit ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"

ws ::= ( " " | "\t" | "\n" | "\r" ) { " " | "\t" | "\n" | "\r" }
```

