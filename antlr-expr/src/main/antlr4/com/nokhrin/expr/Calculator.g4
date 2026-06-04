grammar Calculator;

// программа
program : statement+ EOF;

// инструкция
statement : assignment | expression;

// присваивание
assignment : ID '=' expression;

// выражения
expression  : sum;
sum   : mul ((PLUS | MINUS) mul)*;
mul   : pow ((MUL | DIV) pow)*;
pow   : unary (POW pow)?;
unary : (PLUS | MINUS) unary | fact;
fact  : prime (EXCL)?;
prime : NUM | ID | MOD expression MOD | LPAR expression? RPAR;

// лексемы
ID    : LETTER (LETTER | DIGIT)*;
NUM   : DIGIT+ (SEP DIGIT*)? | SEP DIGIT+;
EXCL: '!';
POW : '^';
MOD : '|';
PLUS : '+';
MINUS : '-';
MUL : '*';
DIV : '/';
LPAR : '(';
RPAR : ')';
SEP : '.';
DIGIT : [0-9];
LETTER : [a-zA-Z];
WS : [ \t\n\r]+ ->skip;