package edu.patterns;

import edu.tokens.TokenType;
import java.util.regex.Pattern;

public class NumberPattern implements LiteralPattern {
  @Override
  public Pattern getPattern() {
    return java.util.regex.Pattern.compile("[0-9]+");
  }

  @Override
  public TokenType getTokenType() {
    return TokenType.LITERAL;
  }
}