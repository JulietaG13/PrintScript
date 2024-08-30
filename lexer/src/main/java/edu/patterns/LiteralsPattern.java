package edu.patterns;

import edu.tokens.TokenType;
import java.util.List;
import java.util.regex.Pattern;

public class LiteralsPattern implements TokenPattern {
  private List<LiteralPattern> patterns;

  public LiteralsPattern(List<LiteralPattern> patterns) {
    this.patterns = patterns;
  }

  @Override
  public Pattern getPattern() {
    return Pattern.compile(
        patterns.stream()
            .map(LiteralPattern::getPattern)
            .map(Pattern::pattern)
            .reduce((a, b) -> a + "|" + b)
            .orElse(""));
  }

  @Override
  public TokenType getTokenType() {
    return TokenType.LITERAL;
  }
}
