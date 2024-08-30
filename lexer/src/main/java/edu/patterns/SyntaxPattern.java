package edu.patterns;

import edu.tokens.TokenType;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SyntaxPattern implements TokenPattern {

  private final List<String> syntaxes;

  public SyntaxPattern(List<String> syntaxes) {
    this.syntaxes = syntaxes;
  }

  @Override
  public Pattern getPattern() {
    String patternString = syntaxes.stream().map(Pattern::quote).collect(Collectors.joining("|"));
    return Pattern.compile(patternString);
  }

  @Override
  public TokenType getTokenType() {
    return TokenType.SYNTAX;
  }
}
