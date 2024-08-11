package edu;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PatternManager {
  private final List<String> keywords;
  private final List<String> operators;
  private final List<String> syntaxes;
  private final LinkedHashMap<Pattern, TokenType> patterns;

  public PatternManager() {
    this.keywords = List.of("String", "Number", "let");
    this.operators = List.of("+=", "-=", "*=", "/=", "%", "+", "-", "*", "/", "=");
    this.syntaxes = List.of(";", ":", "(", ")", "{", "}", ",");
    this.patterns = new LinkedHashMap<>();
    initializePatterns();
  }

  private PatternManager(List<String> keywords, List<String> operators, List<String> syntaxes) {
    this.keywords = keywords;
    this.operators = operators;
    this.syntaxes = syntaxes;
    this.patterns = new LinkedHashMap<>();
    initializePatterns();
  }

  public PatternManager addKeyword(String keyword){
    List<String> new_keywords = new ArrayList<>(keywords);
    new_keywords.add(keyword);
    return new PatternManager(new_keywords, operators, syntaxes);
  }

  public PatternManager addOperator(String operator){
    List<String> new_operators = new ArrayList<>(operators);
    new_operators.add(operator);
    return new PatternManager(keywords, new_operators, syntaxes);
  }

  public PatternManager addSyntax(String syntax){
    List<String> new_syntaxes = new ArrayList<>(syntaxes);
    new_syntaxes.add(syntax);
    return new PatternManager(keywords, operators, new_syntaxes);
  }

  private void initializePatterns() {
    addPattern(getKeywordPattern(), TokenType.KEYWORD);
    addPattern(getIdentifierPattern(), TokenType.IDENTIFIER);
    addPattern(getLiteralPattern(), TokenType.LITERAL);
    addPattern(getOperatorPattern(), TokenType.OPERATOR);
    addPattern(getSyntaxPattern(), TokenType.SYNTAX);
  }

  private Pattern getKeywordPattern() {
    String patternString = keywords.stream()
      .map(Pattern::quote)
      .collect(Collectors.joining("|", "\\b(", ")\\b"));
    return Pattern.compile(patternString);
  }

  private Pattern getOperatorPattern() {
    String patternString = operators.stream()
      .map(Pattern::quote)
      .collect(Collectors.joining("|"));
    return Pattern.compile(patternString);
  }

  private Pattern getSyntaxPattern() {
    String patternString = syntaxes.stream()
      .map(Pattern::quote)
      .collect(Collectors.joining("|"));
    return Pattern.compile(patternString);
  }

  private Pattern getIdentifierPattern() {
    return Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*|println");
  }

  private Pattern getLiteralPattern() {
    return Pattern.compile("[0-9]+|\"[^\"]*\"");
  }

  private void addPattern(Pattern pattern, TokenType tokenType) {
    patterns.put(pattern, tokenType);
  }

  public LinkedHashMap<Pattern, TokenType> getPatterns() {
    return patterns;
  }
}