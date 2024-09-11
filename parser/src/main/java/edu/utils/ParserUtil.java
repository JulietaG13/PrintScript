package edu.utils;

import edu.tokens.Token;
import edu.tokens.TokenType;
import java.util.List;

public class ParserUtil {

  public static boolean isKeyword(List<Token> tokens, int i) {
    return tokens.get(i).getType() == TokenType.KEYWORD;
  }

  public static boolean isIdentifier(List<Token> tokens, int i) {
    return tokens.get(i).getType() == TokenType.IDENTIFIER;
  }

  public static boolean isLiteral(List<Token> tokens, int i) {
    return isLiteral(tokens.get(i));
  }

  public static boolean isLiteral(Token token) {
    return token.getType() == TokenType.LITERAL;
  }

  public static boolean isOperator(List<Token> tokens, int i) {
    return isOperator(tokens.get(i));
  }

  public static boolean isOperator(Token token) {
    return token.getType() == TokenType.OPERATOR;
  }

  public static boolean isSyntax(List<Token> tokens, int i) {
    return isSyntax(tokens.get(i));
  }

  public static boolean isSyntax(Token token) {
    return token.getType() == TokenType.SYNTAX;
  }

  public static boolean isOpenParen(List<Token> tokens, int i) {
    return isOpenParen(tokens.get(i));
  }

  public static boolean isOpenParen(Token token) {
    return isSyntax(token) && token.getContent().equals("(");
  }

  public static boolean isCloseParen(List<Token> tokens, int i) {
    return isCloseParen(tokens.get(i));
  }

  public static boolean isCloseParen(Token token) {
    return isSyntax(token) && token.getContent().equals(")");
  }

  public static boolean isArgSeparator(List<Token> tokens, int i) {
    return isArgSeparator(tokens.get(i));
  }

  public static boolean isArgSeparator(Token token) {
    return isSyntax(token) && token.getContent().equals(",");
  }

  public static boolean isSemicolon(List<Token> tokens, int i) {
    return isSemicolon(tokens.get(i));
  }

  public static boolean isSemicolon(Token token) {
    return isSyntax(token) && token.getContent().equals(";");
  }

  public static boolean isOpenBracket(List<Token> tokens, int i) {
    return isOpenBracket(tokens.get(i));
  }

  public static boolean isOpenBracket(Token token) {
    return isSyntax(token) && token.getContent().equals("{");
  }

  public static boolean isCloseBracket(List<Token> tokens, int i) {
    return isCloseBracket(tokens.get(i));
  }

  public static boolean isCloseBracket(Token token) {
    return isSyntax(token) && token.getContent().equals("}");
  }

  public static boolean isColon(List<Token> tokens, int i) {
    return isColon(tokens.get(i));
  }

  public static boolean isColon(Token token) {
    return isSyntax(token) && token.getContent().equals(":");
  }

  public static boolean isEndOfStatement(Token token) {
    return isSemicolon(token) || isCloseBracket(token);
  }

  public static boolean isAssign(List<Token> tokens, int i) {
    return isAssign(tokens.get(i));
  }

  public static boolean isAssign(Token token) {
    String content = token.getContent();
    return isOperator(token) && content.equals("=");
  }
}
