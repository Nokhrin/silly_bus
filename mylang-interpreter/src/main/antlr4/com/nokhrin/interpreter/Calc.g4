grammar Calc;

prog : stat+ EOF;
stat : assign NEWLINE
    | expr NEWLINE
    ;
assign : ID '=' expr ;
expr  : mul ((ADD | SUB) mul)* ;
mul   : unary ((MUL | DIV) unary)* ;
unary : (ADD | SUB) unary | primary ;
primary : NUM | ID | '(' expr ')' ;

ID    : LETTER ('_' | LETTER | DIGIT)* ;
NUM   : DIGIT+ ('.' DIGIT*)? | '.' DIGIT+ ;
MUL : '*' ;
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;
fragment LETTER : [a-zA-Z] ;
fragment DIGIT : [0-9] ;
NEWLINE : '\r'? '\n' ;
WS : [ \t\r\n]+ -> skip ;