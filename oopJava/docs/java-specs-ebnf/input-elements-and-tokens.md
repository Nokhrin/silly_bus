# элементы ввода и лексемы

элементы ввода - части последовательности, обрабатываемой компилятором
> пример
> x = y + 10;
> каждый символ суть элемент ввода: x, =, , y, +, , 1, 0, ;

лексемы - группы символов, объединенные по смыслу
> id: x, y; number: 10; addOp: +; semicolon: ;

КС-грамматика описывает синтаксическую структуру языка на уровне лексем

---

> Input:
>   {InputElement} [Sub]

input ::= {input_element} [sub]
input_element ::= whitespace | comment | token
token ::= identifier | keyword | literal | separator | operator
sub ::= %x1A  // управляющий символ Substitute, ASCII 26

---

> InputElement:
>   WhiteSpace
>   Comment
>   Token

input_element ::= whitespace | comment | token

> WhiteSpace:
> the ASCII SP character, also known as "space"
> the ASCII HT character, also known as "horizontal tab"
> the ASCII FF character, also known as "form feed"
> LineTerminator

> LineTerminator:
> the ASCII LF character, also known as "newline"
> the ASCII CR character, also known as "return"
> the ASCII CR character followed by the ASCII LF character
> InputCharacter:
> UnicodeInputCharacter but not CR or LF

whitespace ::= sp_char | ht_char | ff_char | line_terminator
line_terminator ::= lf_char | cr_char | (cr_char, lf_char)
input_character ::= 

> UnicodeInputCharacter:
> UnicodeEscape
> RawInputCharacter
> UnicodeEscape:
> \ UnicodeMarker HexDigit HexDigit HexDigit HexDigit
> UnicodeMarker:
> u {u}
> HexDigit:
> (one of)
> 0 1 2 3 4 5 6 7 8 9 a b c d e f A B C D E F
> RawInputCharacter:
> any Unicode character representable in UTF-16


> Token:
>   Identifier
>   Keyword
>   Literal
>   Separator
>   Operator

token ::= identifier | keyword | literal | separator | operator

> Sub:
>   the ASCII SUB character, also known as "control-Z"

sub ::= %x1A  // управляющий символ Substitute, ASCII 26