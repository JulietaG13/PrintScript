package lexer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.LexicalRange;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

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
  public void testAddPattern() {
    Pattern customPattern = Pattern.compile("\\bcustom\\b");
    lexer.addPattern(customPattern, TokenType.KEYWORD);
    assertTrue(lexer.patterns.containsKey(customPattern));
  }

  @Test
  public void testGetCharAt() {
    Optional<Character> charAt0 = lexer.getCharAt(new LexicalRange(0, 0, 0));
    assertTrue(charAt0.isPresent());
    assertEquals('l', charAt0.get());

    Optional<Character> charAtOutOfBounds = lexer.getCharAt(new LexicalRange(100, 0, 0));
    assertFalse(charAtOutOfBounds.isPresent());
  }

  @Test
  public void testAdvancePosition() {
    lexer.advancePosition(4);
    assertEquals(4, lexer.currentPosition.getOffset());
    assertEquals(4, lexer.currentPosition.getColumn());
  }

  @Test
  public void testIsWholeWordMatch() {
    lexer = new Lexer("letx");
    lexer.currentPosition = new LexicalRange(0, 0, 0);
    assertFalse(lexer.isWholeWordMatch("let"));

    lexer = new Lexer("let x");
    lexer.currentPosition = new LexicalRange(0, 0, 0);
    assertTrue(lexer.isWholeWordMatch("let"));
  }

  @Test
  public void testInvalidToken() {
    Lexer invalidLexer = new Lexer("let x = 42 @;");
    Exception exception = assertThrows(RuntimeException.class, invalidLexer::tokenize);
    assertTrue(exception.getMessage().contains("Invalid token"));
  }
}