package edu;

import edu.tokens.Token;
import edu.tokens.TokenType;

public class TokenProvider {

  public static Token getLet() {
    return new Token(TokenType.KEYWORD, "let", null, null);
  }

  public static Token getNumber() {
    return new Token(TokenType.KEYWORD, "number", null, null);
  }

  public static Token getString() {
    return new Token(TokenType.KEYWORD, "string", null, null);
  }

  public static Token getOperator(String operator) {
    return new Token(TokenType.OPERATOR, operator, null, null);
  }

  public static Token getLiteral(Double literal) {
    return new Token(TokenType.LITERAL, literal.toString(), null, null);
  }

  public static Token getLiteral(Integer literal) {
    return new Token(TokenType.LITERAL, literal.toString(), null, null);
  }

  public static Token getLiteral(String literal) {
    return new Token(TokenType.LITERAL, "\"" + literal + "\"", null, null);
  }

  public static Token getIdentifier(String name) {
    return new Token(TokenType.IDENTIFIER, name, null, null);
  }

  public static Token getSemicolon() {
    return new Token(TokenType.SYNTAX, ";", null, null);
  }

  public static Token getColon() {
    return new Token(TokenType.SYNTAX, ":", null, null);
  }

  public static Token getOpenParen() {
    return new Token(TokenType.SYNTAX, "(", null, null);
  }

  public static Token getCloseParen() {
    return new Token(TokenType.SYNTAX, ")", null, null);
  }

  public static Token getComma() {
    return new Token(TokenType.SYNTAX, ",", null, null);
  }

  public static Token getEquals() {
    return new Token(TokenType.OPERATOR, "=", null, null);
  }
}
