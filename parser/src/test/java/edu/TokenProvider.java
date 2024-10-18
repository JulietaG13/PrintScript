package edu;

import edu.tokens.Token;
import edu.tokens.TokenType;
import java.math.BigDecimal;

public class TokenProvider {
  private static final LexicalRange range = new LexicalRange(0, 0, 0);

  public static Token getLet() {
    return new Token(TokenType.KEYWORD, "let", range, range);
  }

  public static Token getConst() {
    return new Token(TokenType.KEYWORD, "const", range, range);
  }

  public static Token getNumber() {
    return new Token(TokenType.KEYWORD, "number", range, range);
  }

  public static Token getString() {
    return new Token(TokenType.KEYWORD, "string", range, range);
  }

  public static Token getBoolean() {
    return new Token(TokenType.KEYWORD, "boolean", range, range);
  }

  public static Token getOperator(String operator) {
    return new Token(TokenType.OPERATOR, operator, range, range);
  }

  public static Token getLiteral(BigDecimal literal) {
    return new Token(TokenType.LITERAL, literal.toString(), range, range);
  }

  public static Token getLiteral(String literal) {
    return new Token(TokenType.LITERAL, "\"" + literal + "\"", range, range);
  }

  public static Token getIdentifier(String name) {
    return new Token(TokenType.IDENTIFIER, name, range, range);
  }

  public static Token getSemicolon() {
    return new Token(TokenType.SYNTAX, ";", range, range);
  }

  public static Token getColon() {
    return new Token(TokenType.SYNTAX, ":", range, range);
  }

  public static Token getOpenParen() {
    return new Token(TokenType.SYNTAX, "(", range, range);
  }

  public static Token getCloseParen() {
    return new Token(TokenType.SYNTAX, ")", range, range);
  }

  public static Token getComma() {
    return new Token(TokenType.SYNTAX, ",", range, range);
  }

  public static Token getEquals() {
    return new Token(TokenType.OPERATOR, "=", range, range);
  }
}
