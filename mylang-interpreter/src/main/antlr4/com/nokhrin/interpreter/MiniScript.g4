grammar MiniScript;

prog : (NEWLINE* stat NEWLINE*)* EOF ;
stat : assignStat
     | ifStat
     | whileStat
     | breakStat
     | continueStat
     | returnStat
     | funcDef
     | block
     | expr
     ;

funcDef : 'def' funcSignature ;
funcSignature : ID '(' parameters? ')' block ;
parameters : ID (',' ID)* ;

block: '{' NEWLINE* (stat NEWLINE*)* '}' ;
returnStat : 'return' expr ;

assignStat : ID '=' expr ;
ifStat : 'if' expr 'then' NEWLINE* stat ('else' NEWLINE* stat)? ;
whileStat : 'while' expr 'do' NEWLINE* stat ;
breakStat : 'break' ;
continueStat : 'continue' ;

callExpr : ID '(' arguments? ')' ;
arguments : expr (',' expr)* ;

expr  : orExpr ;
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
     | callExpr             #funcCall
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