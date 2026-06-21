grammar CSV;

file : hdr row+ EOF ;
hdr : row ;
row : field (',' field)* '\r'? '\n' ;
field
    : STRING        # str
    | PLAIN         # plain
    |               # empty
    ;

STRING : '"' ('""'|~'"')* '"' ;
PLAIN : ~[,"\n\r]+ ;