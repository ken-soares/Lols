package lols;

public enum TokenType {
    // single character tokens
    L_PAREN, R_PAREN, L_BRACE, R_BRACE, DOT, MINUS, PLUS, COMMA,
    SEMICOLON, SLASH, STAR,

    // one or two character tokens
    BANG, BANG_EQUAL, EQUAL, EQUAL_EQUAL, GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,

    // literals
    STRING, NUMBER, IDENTIFIER,

    // keywords
    AND, CLASS, ELSE, FALSE, FUNC, FOR, IF, NIL, OR, PRINT, RET,
    SUPER, THIS, TRUE, VAR, WHILE, EOF

}