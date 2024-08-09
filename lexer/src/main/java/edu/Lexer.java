package edu;

import edu.utils.LexicalRange;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
  private final String code;
  LexicalRange currentPosition;
  LinkedHashMap<Pattern, TokenType> patterns;
  private List<Token> tokens;

  public Lexer(String code) {
    this.code = code;
    this.currentPosition = new LexicalRange(0, 0, 0);
    this.tokens = new ArrayList<>();
    this.patterns = new LinkedHashMap<>();
    createPatterns();
  }

  public List<Token> getTokens() {
    return tokens;
  }

  public void addPattern(Pattern pattern, TokenType type) {
    patterns.put(pattern, type);
  }

  public void advancePosition(int length) {
    for (int i = 0; i < length; i++) {
      char c = code.charAt(currentPosition.getOffset());
      currentPosition = new LexicalRange(
        currentPosition.getOffset() + 1,
        c == '\n' ? currentPosition.getLine() + 1 : currentPosition.getLine(),
        c == '\n' ? 0 : currentPosition.getColumn() + 1
      );
    }
  }

  public void createPatterns() {
    addPattern(Pattern.compile("\\blet\\b|\\bString\\b|\\bNumber\\b"), TokenType.KEYWORD);  // Keywords
    addPattern(Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*|println"), TokenType.IDENTIFIER); // Identifier
    addPattern(Pattern.compile("[0-9]+"), TokenType.LITERAL); // Integer literal
    addPattern(Pattern.compile("\"[^\"]*\""), TokenType.LITERAL); // String literal
    addPattern(Pattern.compile("\\+=|-=|/=|\\*=|==|[+\\-*/=%]"), TokenType.OPERATOR); // Operators
    addPattern(Pattern.compile("[;:(){},]"), TokenType.SYNTAX); // Syntax including parentheses, braces, and semicolon
  }

  public Optional<Character> getCharAt(LexicalRange position) {
    if (position.getOffset() >= code.length()) {
      return Optional.empty();
    }
    return Optional.of(code.charAt(position.getOffset()));
  }

  public void tokenize() {
    while (currentPosition.getOffset() < code.length()) {
      Optional<Character> currentChar = getCharAt(currentPosition);
      if (currentChar.isEmpty()) {
        break;
      }

      if (Character.isWhitespace(currentChar.get())) {
        advancePosition(1);
        continue;
      }

      boolean matched = false;
      for (Map.Entry<Pattern, TokenType> entry : patterns.entrySet()) {
        Pattern pattern = entry.getKey();
        TokenType type = entry.getValue();
        String sub = code.substring(currentPosition.getOffset());
        Matcher matcher = pattern.matcher(sub);

        if (matcher.lookingAt()) {
          String tokenValue = matcher.group();
          if (type == TokenType.KEYWORD) {
            if (!isWholeWordMatch(tokenValue)) {
              continue;
            }
          }
          LexicalRange startRange = new LexicalRange(
            currentPosition.getOffset(), currentPosition.getLine(), currentPosition.getColumn());
          LexicalRange endRange = new LexicalRange(
            currentPosition.getOffset() + tokenValue.length() - 1, currentPosition.getLine(), currentPosition.getColumn() + tokenValue.length() - 1);

          tokens.add(new Token(type, tokenValue, startRange, endRange));
          advancePosition(tokenValue.length());
          matched = true;
          break;
        }
      }

      if (!matched) {
        throw new RuntimeException("Invalid token: " + currentChar.get());
      }
    }
  }

  public boolean isWholeWordMatch(String tokenValue) {
    int endOffset = currentPosition.getOffset() + tokenValue.length();
    if (endOffset < code.length()) {
      char nextChar = code.charAt(endOffset);
      return !Character.isLetterOrDigit(nextChar) && nextChar != '_';
    }
    return true;
  }

}
