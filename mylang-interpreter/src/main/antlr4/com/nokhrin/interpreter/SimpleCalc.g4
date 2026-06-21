grammar SimpleCalc;

stat : expr ;
expr : expr MULT expr   # Mult
     | expr ADD expr    # Add
     | INT              # Num
     ;

MULT : '*' ;
ADD : '+' ;
INT : [1-9] [0-9]* ;
WS : [ \t\r\n] -> skip ;