grammar C;

prog: (var | func)* EOF ;
var: type ID (EQ expr)? LINETERM ;
func: type ID '(' params? ')' block ;
params: param (',' param)* ;
param: type ID ;
block: '{' stat* '}' ;
stat: block
    | var
    | 'if' expr 'then' stat ('else' stat)?
    | 'return' expr? LINETERM
    | expr EQ expr
    | expr LINETERM
    ;
expr: ID '(' exprs? ')'
    | expr '[' expr ']'
    | '-' expr
    | '!' expr
    | expr '*' expr
    | expr ('+'|'-') expr
    | expr '==' expr
    | COMMENT
    | ID
    | INT
    | '(' expr ')'
    ;
exprs: expr (',' expr)* ;
type: 'int' | 'float' | 'void' ;

ID: LETTER (LETTER | DIGIT)* ;
EQ: '=' ;
LINETERM: ';' ;
INT: DIGIT | [1-9] DIGIT* ;
LETTER: [a-zA-Z] ;
DIGIT: [0-9] ;
WS: [ \t\n\r]+ -> channel(1) ;
COMMENT: '//' .*? '\n' -> channel(2) ;