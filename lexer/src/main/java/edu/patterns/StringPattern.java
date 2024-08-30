package edu.patterns;

import edu.tokens.TokenType;
import java.util.regex.Pattern;

public class StringPattern implements LiteralPattern {
  @Override
  public Pattern getPattern() {
    return java.util.regex.Pattern.compile("\"[^\"]*\"");
  }

  @Override
  public TokenType getTokenType() {
    return TokenType.LITERAL;
  }
}
