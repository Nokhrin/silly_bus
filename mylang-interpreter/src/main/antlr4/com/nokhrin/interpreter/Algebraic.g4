grammar Algebraic;

prog : stat* EOF ;
stat : expr NL
     | expr EOF
     | NL
     ;
expr  : ID '=' expr                 # assign
      | expr '^'<assoc=right> expr  # pow
      | expr ('*' | '/') expr       # mul
      | ('+' | '-') expr            # un
      | expr ('+' | '-') expr       # sum
      | expr '!'                    # fact
      | '|' expr '|'                # mod
      | '(' expr ')'                # group
      | ID                          # id
      | NUM                         # num
      ;

ID    : LETTER (LETTER | DIGIT)*;
NUM   : DIGIT+ ('.' DIGIT*)? | '.' DIGIT+;
fragment DIGIT : [0-9];
fragment LETTER : [a-zA-Z];
NL : '\r'? '\n' ;
WS : [ \t]+ ->skip;