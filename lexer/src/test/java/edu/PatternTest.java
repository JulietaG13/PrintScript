package edu;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.patterns.BooleanPattern;
import edu.patterns.NumberPattern;
import edu.patterns.StringPattern;
import edu.tokens.TokenType;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

public class PatternTest {

  @Test
  void testBooleanPatternMatcher() {
    BooleanPattern booleanPattern = new BooleanPattern();
    Pattern pattern = booleanPattern.getPattern();
    assertTrue(pattern.matcher("true").matches());
    assertTrue(pattern.matcher("false").matches());
    TokenType tokenType = booleanPattern.getTokenType();
    assertTrue(tokenType == TokenType.LITERAL);
  }

  @Test
  void testNumberPatternMatcher() {
    NumberPattern numberPattern = new NumberPattern();
    Pattern pattern = numberPattern.getPattern();
    assertTrue(pattern.matcher("123").matches());
    assertTrue(pattern.matcher("123456").matches());
    TokenType tokenType = numberPattern.getTokenType();
    assertTrue(tokenType == TokenType.LITERAL);
  }

  @Test
  void testStringPatternMatcher() {
    StringPattern stringPattern = new StringPattern();
    Pattern pattern = stringPattern.getPattern();
    assertTrue(pattern.matcher("\"Hello World\"").matches());
    TokenType tokenType = stringPattern.getTokenType();
    assertTrue(tokenType == TokenType.LITERAL);
  }
}
