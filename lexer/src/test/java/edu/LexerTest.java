package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.tokens.Token;
import edu.tokens.TokenType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LexerTest {

  private Lexer lexer;

  @BeforeEach
  public void setup() {
    lexer = new Lexer("let x = 42; println(\"Hello, World!\");");
  }

  @Test
  public void testTokenize() {
    lexer.tokenize();
    List<Token> tokens = lexer.getTokens();

    assertEquals(10, tokens.size());

    assertEquals(TokenType.KEYWORD, tokens.get(0).getType());
    assertEquals("let", tokens.get(0).getContent());

    assertEquals(TokenType.IDENTIFIER, tokens.get(1).getType());
    assertEquals("x", tokens.get(1).getContent());

    assertEquals(TokenType.OPERATOR, tokens.get(2).getType());
    assertEquals("=", tokens.get(2).getContent());

    assertEquals(TokenType.LITERAL, tokens.get(3).getType());
    assertEquals("42", tokens.get(3).getContent());

    assertEquals(TokenType.SYNTAX, tokens.get(4).getType());
    assertEquals(";", tokens.get(4).getContent());

    assertEquals(TokenType.IDENTIFIER, tokens.get(5).getType());
    assertEquals("println", tokens.get(5).getContent());

    assertEquals(TokenType.SYNTAX, tokens.get(6).getType());
    assertEquals("(", tokens.get(6).getContent());

    assertEquals(TokenType.LITERAL, tokens.get(7).getType());
    assertEquals("\"Hello, World!\"", tokens.get(7).getContent());

    assertEquals(TokenType.SYNTAX, tokens.get(8).getType());
    assertEquals(")", tokens.get(8).getContent());

    assertEquals(TokenType.SYNTAX, tokens.get(9).getType());
    assertEquals(";", tokens.get(9).getContent());
  }

  @Test
  public void testInvalidToken() {
    Lexer invalidLexer = new Lexer("let x = 42 @;");
    Exception exception = assertThrows(RuntimeException.class, invalidLexer::tokenize);
    assertTrue(exception.getMessage().contains("Invalid token"));
  }

  @Test
  public void testLexicalRangeToString() {
    LexicalRange range = new LexicalRange(15, 5, 1);
    assertEquals("LexicalRange(offset=15, line=5, column=1)", range.toString());
  }

  @Test
  public void testPatternManager() {
    PatternManager patternManager = new PatternManager();
    PatternManager patternManager1 = patternManager.addKeyword("let");
  }
}
