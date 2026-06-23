grammar C;

prog: (funcDecl | varDecl )* EOF ;
varDecl: type ID (EQ expr)? LINETERM? ;
funcDecl: type ID '(' params? ')' block ;
params: param (',' param)* ;
param: type ID ;
block: '{' stat* '}' ;
stat: block
    | varDecl
    | 'if' expr 'then' stat ('else' stat)?
    | 'return' expr? LINETERM
    | expr EQ expr LINETERM
    | expr LINETERM
    ;
expr: ID '(' exprs? ')'    #call
    | expr '[' expr ']'    #index
    | '-' expr             #negate
    | '!' expr             #not
    | expr '*' expr        #mult
    | expr ('+'|'-') expr  #addSub
    | expr '==' expr       #eq
    | ID                   #var
    | INT                  #int
    | COMMENT              #comment
    | '(' expr ')'         #parens
    ;
exprs: expr (',' expr)* ;
type: INT_TYPE | FLOAT_TYPE | VOID_TYPE ;

INT_TYPE: 'int' ;
FLOAT_TYPE: 'float' ;
VOID_TYPE: 'void' ;
ID: LETTER (LETTER | DIGIT)* ;
EQ: '=' ;
LINETERM: ';' ;
INT: DIGIT | [1-9] DIGIT* ;
LETTER: [a-zA-Z] ;
DIGIT: [0-9] ;
WS: [ \t\n\r]+ -> channel(1) ;
COMMENT: '//' .*? '\n' -> channel(2) ;