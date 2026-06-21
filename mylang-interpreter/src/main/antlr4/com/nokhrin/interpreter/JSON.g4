grammar JSON;
json : value EOF ;
obj
    : '{' pair (',' pair)* '}'  #nonEmptyObject
    | '{' WS* '}'               #emptyObject
    ;
arr
    : '[' value (',' value)* ']' #nonEmptyArray
    | '[' WS* ']'                #emptyArray
    ;
pair : STR ':' value ;
value
    : obj       #objectValue
    | arr       #arrayValue
    | STR       #stringValue
    | NUM       #atomValue
    | 'true'    #atomValue
    | 'false'   #atomValue
    | 'null'    #atomValue
    ;

STR : '"' .*? '"' ;
NUM
    : DIGITS '.' DIGITS EXP?
    | DIGITS EXP
    | DIGITS
    ;
INT : DIGITS ;
fragment EXP : [eE] [+-]? DIGITS ;
fragment DIGITS : [0-9]+ ;
WS : [ \t\r\n] -> skip ;