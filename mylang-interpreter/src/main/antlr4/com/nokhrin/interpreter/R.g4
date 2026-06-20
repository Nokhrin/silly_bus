grammar R;

prog: (stat (';' | NL)? | NL)* | stat?
    EOF
    ;
stat: expr ;

expr: '{' exprs '}'
    | expr '[[' sublist ']' ']'
    | expr '[' sublist ']'
    | expr ('::' | ':::') expr
    | expr ('$' | '@') expr
    | expr '^'<assoc=right> expr
    | ('-' | '+') expr
    | expr ':' expr
    | expr ('*' | '/') expr
    | expr ('+' | '-') expr
    | expr ('==' | '!=' | '>' | '<' | '>=' | '<=') expr
    | '!' expr
    | expr ('&' | '&&') expr
    | expr ('|' | '||') expr
    | '~' expr
    | '?'
    | 'function' '(' forms? ')' expr
    | expr '~' expr
    | expr ('<-' | '=' | '<<-') expr
    | expr ('->' | '->>' | ':=') expr
    | expr '(' sublist? ')'
    | ID
    ;
exprs: expr ((';' | NL) expr?)* ;
forms: form (',' form)* ;
form: ID
    | ID '=' expr
    | '...'
    ;
sublist: sub (',' sub)* ;
sub : expr
    | ID '=' expr?
    | STRING '=' expr?
    | 'NULL' '=' expr?
    | '...'
    ;

ID  : '.' (LETTER | '_' | '.') (LETTER | DIGIT | '_' | '.')*
    | (LETTER | DIGIT | '_' | '.')+
    ;
fragment LETTER: [a-zA-Z] ;
fragment DIGIT: [0-9] ;
NL: '\r'? '\n' ;
STRING: '"' .*? '"' ;
WS: [ \t] -> skip ;