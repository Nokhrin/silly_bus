grammar JSON;
json : elem (',' elem)* ;
elem: WS* value WS* ;
obj : '{' WS* '}' | '{' pair (',' pair)* '}' ;
arr : '[' WS* ']' | '[' value (',' value)* ']' ;
pair : STR ':' value ;
num: INT? FRACT? EXP? ;
value
    : obj
    | arr
    | STR
    | num
    | 'false'
    | 'true'
    | 'null'
    ;

STR : '"' .*? '"' ;
INT : DIGITS ;
FRACT
    : 'E' ('+' | '-')? DIGITS
    | 'e' ('+' | '-')? DIGITS
    ;
EXP : DIGITS ;
fragment DIGITS : [0-9]+ ;
WS : [ \t\r\n] -> skip ;