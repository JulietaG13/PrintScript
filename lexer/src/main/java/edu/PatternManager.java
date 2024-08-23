package edu;

import edu.tokens.TokenType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PatternManager {
  private final List<String> keywords;
  private final List<String> operators;
  private final List<String> syntaxes;
  private final LinkedHashMap<PatternMatcher, TokenType> matchers;

  public PatternManager() {
    this.keywords = List.of("String", "Number", "let");
    this.operators = List.of("+=", "-=", "*=", "/=", "%", "+", "-", "*", "/", "=");
    this.syntaxes = List.of(";", ":", "(", ")", "{", "}", ",");
    this.matchers = new LinkedHashMap<>();
    initializePatterns();
  }

  private PatternManager(List<String> keywords, List<String> operators, List<String> syntaxes) {
    this.keywords = keywords;
    this.operators = operators;
    this.syntaxes = syntaxes;
    this.matchers = new LinkedHashMap<>();
    initializePatterns();
  }

  public PatternManager addKeyword(String keyword) {
    List<String> newKeywords = new ArrayList<>(keywords);
    newKeywords.add(keyword);
    return new PatternManager(newKeywords, operators, syntaxes);
  }

  public PatternManager addOperator(String operator) {
    List<String> newOperators = new ArrayList<>(operators);
    newOperators.add(operator);
    return new PatternManager(keywords, newOperators, syntaxes);
  }

  public PatternManager addSyntax(String syntax) {
    List<String> newSyntaxes = new ArrayList<>(syntaxes);
    newSyntaxes.add(syntax);
    return new PatternManager(keywords, operators, newSyntaxes);
  }

  private void initializePatterns() {
    addPattern(getKeywordPattern(), TokenType.KEYWORD);
    addPattern(getIdentifierPattern(), TokenType.IDENTIFIER);
    addPattern(getLiteralPattern(), TokenType.LITERAL);
    addPattern(getOperatorPattern(), TokenType.OPERATOR);
    addPattern(getSyntaxPattern(), TokenType.SYNTAX);
  }

  private Pattern getKeywordPattern() {
    String patternString =
        keywords.stream().map(Pattern::quote).collect(Collectors.joining("|", "\\b(", ")\\b"));
    return Pattern.compile(patternString);
  }

  private Pattern getOperatorPattern() {
    String patternString = operators.stream().map(Pattern::quote).collect(Collectors.joining("|"));
    return Pattern.compile(patternString);
  }

  private Pattern getSyntaxPattern() {
    String patternString = syntaxes.stream().map(Pattern::quote).collect(Collectors.joining("|"));
    return Pattern.compile(patternString);
  }

  private Pattern getIdentifierPattern() {
    return Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*|println");
  }

  private Pattern getLiteralPattern() {
    return Pattern.compile("[0-9]+|\"[^\"]*\"");
  }

  private void addPattern(Pattern pattern, TokenType tokenType) {
    matchers.put(new RegexPatternMatcher(pattern, tokenType), tokenType);
  }

  public LinkedHashMap<PatternMatcher, TokenType> getMatchers() {
    return matchers;
  }
}
