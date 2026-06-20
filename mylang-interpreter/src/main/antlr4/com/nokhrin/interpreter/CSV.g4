grammar CSV;

file : hdr row+ ;
hdr : row ;
row : field (',' field)* NL ;
field : QUOTED | NON_QUOTED ;

QUOTED : '"' ('""'|~'"')* '"' ;
NON_QUOTED : ~[,"\n\r]+ ;
NL : '\r'? '\n' ;