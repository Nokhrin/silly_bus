grammar MiniTyped;

prog : (NEWLINE* stat NEWLINE*)* EOF ;
stat : assignStat
     | ifStat
     | whileStat
     | breakStat
     | continueStat
     | returnStat
     | funcDefinition
     | varDeclaration
     | block
     | expr
     ;

funcDefinition : 'def' funcSignature ;
funcSignature : type ID '(' funcParameters? ')' block ;
funcParameters : funcParameter (',' funcParameter)* ;
funcParameter: type ID ;
varDeclaration: type ID ('=' expr)? ;
type: INT_TYPE | FLOAT_TYPE | BOOL_TYPE | VOID_TYPE ;

block: '{' NEWLINE* (stat NEWLINE*)* '}' ;
returnStat : 'return' expr? ;

assignStat : ID '=' expr ;
ifStat : 'if' expr 'then' NEWLINE* stat ('else' NEWLINE* stat)? ;
whileStat : 'while' expr 'do' NEWLINE* stat ;
breakStat : 'break' ;
continueStat : 'continue' ;

callExpr : ID '(' arguments? ')' ;
arguments : expr (',' expr)* ;

expr: ternary ;
ternary: or ('?' ternary ':' ternary)? ;
or: and ('OR' and)* ;
and: comp ('AND' comp)* ;
comp: addSub (('==' | '!=' | '>' | '<' | '>=' | '<=') addSub)? ;
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
     | callExpr             #funcCall
     | ID                 #id
     | '(' expr ')'       #paren
     ;

INT_TYPE : 'int' ;
FLOAT_TYPE : 'float' ;
BOOL_TYPE : 'bool' ;
VOID_TYPE : 'void' ;
COMMENT : '//' ~[\r\n]* -> skip ;
BOOL : 'true' | 'false' ;
ID    : LETTER ('_' | LETTER | DIGIT)* ;
FLOAT : DIGIT+ '.' DIGIT* ([Ee] ('+' | '-')? DIGIT+)?
      | DIGIT+ [Ee] ('+' | '-')? DIGIT+
      | '.' DIGIT+ ;
INT : DIGIT+ ;
fragment LETTER : [a-zA-Z] ;
fragment DIGIT : [0-9] ;
NEWLINE : '\r'? '\n' ;
WS : [ \t]+ -> skip ;