package parser.utils;

import ast.expressions.IdentifierNode;
import lexer.Token;
import lexer.TokenType;

import java.util.List;

import static lexer.TokenType.IDENTIFIER;

public class ParserUtil {

  public static IdentifierNode getIdentifier(Token token) {
    if (token.getType() != IDENTIFIER) {
      throw new RuntimeException(); // TODO()
    }
    return new IdentifierNode(
        token.getStart(),
        token.getEnd(),
        token.getContent()
    );
  }

  public static boolean isTokenOrderValid(List<Token> tokens, List<TokenType> expected) {
    if (expected.size() < tokens.size()) {
      return false;
    }
    for (int i = 0; i < expected.size(); i++) {
      TokenType type = tokens.get(i).getType();
      if (!expected.get(i).equals(type)) {
        return false;
      }
    }
    return true;
  }

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
    return isSyntax(tokens, i) && tokens.get(i).getContent().equals("(");
  }

  public static boolean isCloseParen(List<Token> tokens, int i) {
    return isSyntax(tokens, i) && tokens.get(i).getContent().equals(")");
  }

  public static boolean isArgSeparator(List<Token> tokens, int i) {
    return isArgSeparator(tokens.get(i));
  }

  public static boolean isArgSeparator(Token token) {
    return isSyntax(token) && token.getContent().equals(",");
  }

  public static boolean isEndLine(List<Token> tokens, int i) {
    return isEndLine(tokens.get(i));
  }

  public static boolean isEndLine(Token token) {
    return isSyntax(token) && token.getContent().equals(";");
  }

  public static boolean isAssign(List<Token> tokens, int i) {
    return isAssign(tokens.get(i));
  }

  public static boolean isAssign(Token token) {
    String content = token.getContent();
    return isOperator(token) && (
        content.equals("=") || content.equals("+=") || content.equals("-=") || content.equals("*=") || content.equals("/=")
    );
  }
}
