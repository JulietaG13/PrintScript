package edu.patterns;

import edu.tokens.TokenType;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KeywordPattern implements TokenPattern {

  private final List<String> keywords;

  public KeywordPattern(List<String> keywords) {
    this.keywords = keywords;
  }

  @Override
  public java.util.regex.Pattern getPattern() {
    String patternString =
        keywords.stream().map(Pattern::quote).collect(Collectors.joining("|", "\\b(", ")\\b"));
    return Pattern.compile(patternString);
  }

  @Override
  public TokenType getTokenType() {
    return TokenType.KEYWORD;
  }
}
