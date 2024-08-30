package edu.patterns;

import edu.tokens.TokenType;

public interface TokenPattern {
  java.util.regex.Pattern getPattern();

  TokenType getTokenType();
}
