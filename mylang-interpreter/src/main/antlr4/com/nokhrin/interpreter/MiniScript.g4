grammar MiniScript;

prog : (stat NEWLINE?)* EOF ;
stat : assign
     | expr
     ;
assign : ID '=' expr ;
expr  :  orExpr ;
orExpr: andExpr ('OR' andExpr)* ;
andExpr: compExpr ('AND' compExpr)* ;
compExpr: addSub (('==' | '!=' | '>' | '<' | '>=' | '<=') addSub)? ;
addSub:  mulDiv (('+' | '-') mulDiv)* ;

mulDiv:  unary (('*' | '/') unary)* ;

unary : 'NOT' unary      #not
      | '-' unary  #neg
      | '+' unary  #pos
      | atom       #prime
      ;

atom : FLOAT                #float
     | INT                 #int
     | BOOL                 #bool
     | VOID                 #void
     | ID                 #id
     | '(' expr ')'       #paren
     ;

BOOL : 'true' | 'false' ;
VOID : 'void' ;
ID    : LETTER ('_' | LETTER | DIGIT)* ;
FLOAT : DIGIT+ '.' DIGIT* ([Ee] ('+' | '-')? DIGIT+)?
      | DIGIT+ [Ee] ('+' | '-')? DIGIT+
      | '.' DIGIT+ ;
INT : DIGIT+ ;
fragment LETTER : [a-zA-Z] ;
fragment DIGIT : [0-9] ;
NEWLINE : '\r'? '\n' ;
WS : [ \t]+ -> skip ;