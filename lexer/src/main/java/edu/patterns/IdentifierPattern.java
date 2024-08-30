package edu.patterns;

import edu.tokens.TokenType;
import java.util.regex.Pattern;

public class IdentifierPattern implements TokenPattern {
  @Override
  public Pattern getPattern() {
    return Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*");
  }

  @Override
  public TokenType getTokenType() {
    return TokenType.IDENTIFIER;
  }
}
