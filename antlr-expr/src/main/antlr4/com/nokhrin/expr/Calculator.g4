// Грамматика калькулятора
// expr  ::= sum
// sum   ::= mul { ('+' | '-') mul }
// mul   ::= prime { ('*' | '/') prime }
// prime ::= num | '(' sum ')'
// num   ::= digit {digit}
// digit ::= '0' | '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9'

grammar Calculator;

// правила
expr  : sum;
sum   : mul ((PLUS | MINUS) mul)*;
mul   : prime ((MUL | DIV) prime)*;
prime : num | LPAR sum RPAR;
num   : DIGIT+;

// лексемы
PLUS : '+';
MINUS : '-';
MUL : '*';
DIV : '/';
LPAR : '(';
RPAR : ')';
DIGIT : [0-9];
WS : [ \t\n\r]+ ->skip;