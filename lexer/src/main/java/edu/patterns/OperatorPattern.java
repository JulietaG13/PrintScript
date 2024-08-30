package edu.patterns;

import edu.tokens.TokenType;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class OperatorPattern implements TokenPattern {
  private final List<String> operators;

  public OperatorPattern(List<String> operators) {
    this.operators = operators;
  }

  @Override
  public Pattern getPattern() {
    String patternString = operators.stream().map(Pattern::quote).collect(Collectors.joining("|"));
    return Pattern.compile(patternString);
  }

  @Override
  public TokenType getTokenType() {
    return TokenType.OPERATOR;
  }
}
