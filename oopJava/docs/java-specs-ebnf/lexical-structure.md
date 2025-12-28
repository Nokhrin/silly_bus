# Productions  
  
  
> TypeIdentifier:  
>   Identifier but not permits, record, sealed, var, or yield  
  
type_identifier ::= identifier  // за исключением reserved_keyword  
reserved_keyword ::= reserved_keyword | "permits" | "record" | "sealed" | "var"  
  
---  
  
> UnqualifiedMethodIdentifier:  
>   Identifier but not yield  
  
unqualified_method_identifier ::= identifier  // за исключением reserved_keyword  
reserved_keyword ::= reserved_keyword | "yield"  
  
---  
  
> Identifier:  
>   IdentifierChars but not a ReservedKeyword or BooleanLiteral or NullLiteral  
  
identifier ::= identifier_char { identifier_char }  // за исключением reserved_keyword, boolean_literal, null_literal  
reserved_keyword ::= "abstract" | "assert" | "boolean" | ... ;  // ключевые слова Javа  
boolean_literal ::= "true" | "false"  
null_literal ::= "null"  
  
---  
  
> IdentifierChars:  
>   JavaLetter {JavaLetterOrDigit}  
  
identifier_char ::= java_letter { java_letter_or_digit }  
  
---  
  
> JavaLetter:  
>   any Unicode character that is a "Java letter"  
> БукваДжава:  
>   любой символ Unicode (UTF-16), который суть "Буква языка Джава"  
  
java_letter ::= unicode_char  
unicode_char ::= bmp_char | surrogate_pair  
bmp_char ::= <0x0000-0xd7ff> | <0xe000-0xffff>  
surrogate_pair ::= high_surrogate low_surrogate  
high_surrogate ::= <0xd800-0xdbff>  
low_surrogate ::= <0xdc00-0xdfff>  
  
---  
  
> JavaLetterOrDigit:  
>   any Unicode character that is a "Java letter-or-digit"  
  
java_letter_or_digit ::= unicode_char  
unicode_char ::= bmp_char | surrogate_pair  
bmp_char ::= <0x0000-0xd7ff> | <0xe000-0xffff>  
surrogate_pair ::= high_surrogate low_surrogate  
high_surrogate ::= <0xd800-0xdbff>  
low_surrogate ::= <0xdc00-0xdfff>  
  
---  
  
> Literal:  
>   IntegerLiteral  
>   FloatingPointLiteral  
>   BooleanLiteral  
>   CharacterLiteral  
>   StringLiteral  
>   TextBlock  
>   NullLiteral  
  
Literal ::= IntegerLiteral | FloatingPointLiteral | BooleanLiteral | CharacterLiteral | StringLiteral | TextBlock | NullLiteral  
