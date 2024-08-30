package edu;

import static edu.LexerFactory.createLexerV1;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.tokens.Token;
import edu.tokens.TokenType;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TokenizerTest {
  @Test
  public void testBasicTokenize() {
    String code = "let x = 10;";
    Lexer context = createLexerV1(code);
    context.tokenize();
    assertEquals(5, context.getTokens().size());
    List<Token> tokens = context.getTokens();
    assertEquals(tokens.get(0).getType(), TokenType.KEYWORD);
    assertEquals(tokens.get(0).getEnd().getOffset(), 2);
    assertEquals(tokens.get(0).getStart().getOffset(), 0);
    assertEquals(tokens.get(1).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(1).getStart().getOffset(), 4);
    assertEquals(tokens.get(1).getEnd().getOffset(), 4);
    assertEquals(tokens.get(2).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(2).getStart().getOffset(), 6);
    assertEquals(tokens.get(2).getEnd().getOffset(), 6);
    assertEquals(tokens.get(3).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(3).getStart().getOffset(), 8);
    assertEquals(tokens.get(3).getEnd().getOffset(), 9);
    assertEquals(tokens.get(4).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(4).getEnd().getOffset(), 10);
    assertEquals(tokens.get(4).getStart().getOffset(), 10);
  }

  @Test
  public void testPrintTokenize() {
    String code = "println(10)";
    Lexer context = createLexerV1(code);
    context.tokenize();
    assertEquals(4, context.getTokens().size());
  }

  @Test
  public void testPrefixTokenize() {
    String code = "10+\"hola\"";
    Lexer context = createLexerV1(code);
    context.tokenize();
    assertEquals(3, context.getTokens().size());
    List<Token> tokens = context.getTokens();
    assertEquals(tokens.get(0).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(1).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(2).getType(), TokenType.LITERAL);
  }

  @Test
  public void testWholeWordMatch() {
    String code = "let letdown";
    Lexer context = createLexerV1(code);
    context.tokenize();
    assertEquals(2, context.getTokens().size());
    List<Token> tokens = context.getTokens();
    assertEquals(tokens.get(0).getType(), TokenType.KEYWORD);
    assertEquals(tokens.get(1).getType(), TokenType.IDENTIFIER);
  }

  @Test
  public void testComplexCode() {
    String code = "Number let age = 20;\nNumber let year= 2003; println(age + year);";
    Lexer context = createLexerV1(code);
    context.tokenize();
    assertEquals(19, context.getTokens().size());
    List<Token> tokens = context.getTokens();
    assertEquals(tokens.get(0).getType(), TokenType.KEYWORD);
    assertEquals(tokens.get(1).getType(), TokenType.KEYWORD);
    assertEquals(tokens.get(2).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(3).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(4).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(5).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(6).getType(), TokenType.KEYWORD);
    assertEquals(tokens.get(7).getType(), TokenType.KEYWORD);
    assertEquals(tokens.get(8).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(9).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(10).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(11).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(12).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(13).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(14).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(15).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(16).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(17).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(18).getType(), TokenType.SYNTAX);
  }

  @Test
  public void testFunctionCall() {
    String code = "add(4+1);";
    Lexer context = createLexerV1(code);
    context.tokenize();
    List<Token> tokens = context.getTokens();
    assertEquals(7, tokens.size());
    assertEquals(tokens.get(0).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(1).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(2).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(3).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(4).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(5).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(6).getType(), TokenType.SYNTAX);
  }

  @Test
  public void variableDeclarationWithFunctionCall() {
    String code = "let age: Number=add(19,1);";
    Lexer context = createLexerV1(code);
    context.tokenize();
    List<Token> tokens = context.getTokens();
    assertEquals(12, tokens.size());
    assertEquals(tokens.get(0).getType(), TokenType.KEYWORD);
    assertEquals(tokens.get(1).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(2).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(3).getType(), TokenType.KEYWORD);
    assertEquals(tokens.get(4).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(5).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(6).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(7).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(8).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(9).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(10).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(11).getType(), TokenType.SYNTAX);
  }

  @Test
  public void variableDeclarationWithFunctionCallAnd() {
    String code = "let age: Number=sub(19,1); age+=1;";
    Lexer context = createLexerV1(code);
    context.tokenize();
    List<Token> tokens = context.getTokens();
    assertEquals(16, tokens.size());
    assertEquals(tokens.get(0).getType(), TokenType.KEYWORD);
    assertEquals(tokens.get(1).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(2).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(3).getType(), TokenType.KEYWORD);
    assertEquals(tokens.get(4).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(5).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(6).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(7).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(8).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(9).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(10).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(11).getType(), TokenType.SYNTAX);
    assertEquals(tokens.get(12).getType(), TokenType.IDENTIFIER);
    assertEquals(tokens.get(13).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(14).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(15).getType(), TokenType.SYNTAX);
  }
}
