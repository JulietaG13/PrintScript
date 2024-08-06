package lexer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenizerTest {
  @Test
  public void testBasicTokenize() {
    String code = "let x = 10 ;";
    LexerContext context = new LexerContext(code);
    context.tokenize();
    assertEquals(5, context.getTokens().size());
  }
}
