package edu.patterns;

import edu.tokens.TokenType;
import java.util.regex.Pattern;

public class BooleanPattern implements LiteralPattern {
  @Override
  public Pattern getPattern() {
    return java.util.regex.Pattern.compile("true|false");
  }

  @Override
  public TokenType getTokenType() {
    return TokenType.LITERAL;
  }
}
