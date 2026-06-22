grammar Calc;

prog : stat+ EOF;
stat : assign
     | expr
     ;
assign : ID '=' expr ;
expr  :  addSub ;
addSub:  mulDiv (('+' | '-') mulDiv)* ;
mulDiv:  pow (('*' | '/') pow)* ;
pow : unary ('^' <assoc=right> unary)* ;
unary : '-' unary  #neg
      | '+' unary  #pos
      | unary '!'  #fact
      | atom       #prime
      ;
atom : NUM          #number
     | ID           #id
     | '|' expr '|' #abs
     | '(' expr ')' #paren
     ;

ID    : LETTER ('_' | LETTER | DIGIT)* ;
NUM   : DIGIT+ ('.' DIGIT*)? | '.' DIGIT+ ;
fragment LETTER : [a-zA-Z] ;
fragment DIGIT : [0-9] ;
NEWLINE : '\r'? '\n' -> skip ;
WS : [ \t]+ -> skip ;