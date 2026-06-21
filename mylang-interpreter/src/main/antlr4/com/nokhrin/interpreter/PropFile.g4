grammar PropFile;

file: (prop '\n'?)+ EOF;
prop: ID '=' STRING ;

ID: [a-z]+ ;
STRING: '"' .*? '"' ;