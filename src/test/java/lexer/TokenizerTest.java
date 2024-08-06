package lexer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenizerTest {
  @Test
  public void testBasicTokenize() {
    String code = "let x = 10;";
    LexerContext context = new LexerContext(code);
    context.tokenize();
    assertEquals(5, context.getTokens().size());
  }

  @Test
  public void testPrintTokenize() {
    String code = "println(10)";
    LexerContext context = new LexerContext(code);
    context.tokenize();
    assertEquals(4, context.getTokens().size());
  }

  @Test
  public void testPrefixTokenize() {
    String code = "10+\"hola\"";
    LexerContext context = new LexerContext(code);
    context.tokenize();
    assertEquals(3, context.getTokens().size());
    for (Token token : context.getTokens()) {
      System.out.println(token.getType());
    }
  }
}
