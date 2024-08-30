package edu.patterns;

import edu.tokens.TokenType;
import java.util.regex.Pattern;

public interface TokenPattern {
  Pattern getPattern();

  TokenType getTokenType();
}
