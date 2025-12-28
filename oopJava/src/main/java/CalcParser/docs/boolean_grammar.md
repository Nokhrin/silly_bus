# КС-грамматика для булевых выражений с приоритетом операций: not > and > or

> Документ определяет контекстно-свободную грамматику булевых выражений, применяемую в проекте CalcParser


```ebnf
bool_expr              ::= or_expr { [ws] or_operation [ws] or_expr }
or_expr                ::= and_expr { [ws] and_operation [ws] and_expr }
and_expr               ::= not_expr { [ws] and_operation [ws] not_expr }
not_expr               ::= bool_atom
                        | [ws] unary_not_operation [ws] not_expr [ws]
bool_atom              ::= bool_literal
                        | identifier
                        | '(' [ws] bool_expr [ws] ')'
unary_not_operation    ::= "!" | "not"
or_operation           ::= "||" | "or"
and_operation          ::= "&&" | "and"
bool_literal           ::= "true" | "false"
identifier             ::= letter { letter | digit | "_" }
letter                 ::= "A" | "B" | ... | "Z" | "a" | "b" | ... | "z"
digit                  ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
ws                     ::= (" " | "\t" | "\n" | "\r") { " " | "\t" | "\n" | "\r" }
```