package lexer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
  public void testIsWholeWorldMatch() {
    String code = "letx";
    Lexer context = new Lexer(code);
    context.tokenize();
    assertEquals(1, context.getTokens().size());
    List<Token> tokens = context.getTokens();
    assertEquals(tokens.get(0).getType(), TokenType.IDENTIFIER);

  }
}
