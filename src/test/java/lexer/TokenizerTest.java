package lexer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TokenizerTest {
  @Test
  public void testBasicTokenize() {
    String code = "let x = 10;";
    Lexer context = new Lexer(code);
    context.tokenize();
    assertEquals(5, context.getTokens().size());
  }

  @Test
  public void testPrintTokenize() {
    String code = "println(10)";
    Lexer context = new Lexer(code);
    context.tokenize();
    assertEquals(4, context.getTokens().size());
  }

  @Test
  public void testPrefixTokenize() {
    String code = "10+\"hola\"";
    Lexer context = new Lexer(code);
    context.tokenize();
    assertEquals(3, context.getTokens().size());
    List<Token> tokens = context.getTokens();
    assertEquals(tokens.get(0).getType(), TokenType.LITERAL);
    assertEquals(tokens.get(1).getType(), TokenType.OPERATOR);
    assertEquals(tokens.get(2).getType(), TokenType.LITERAL);
  }

  @Test
  public void testComplexCode() {
    String code = "Number let age = 20;\nNumber let year= 2003; println(age + year);";
    Lexer context = new Lexer(code);
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
  public void testWholeWordMatch() {
    String code = "let letdown";
    Lexer context = new Lexer(code);
    context.tokenize();
    assertEquals(2, context.getTokens().size());
    List<Token> tokens = context.getTokens();
    assertEquals(tokens.get(0).getType(), TokenType.KEYWORD);
    assertEquals(tokens.get(1).getType(), TokenType.IDENTIFIER);
    assertTrue(context.isWholeWordMatch("let"));

  }

}