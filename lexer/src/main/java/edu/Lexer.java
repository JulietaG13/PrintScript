package edu;

import edu.utils.LexicalRange;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
  private final String code;
  LexicalRange currentPosition;
  private final PatternManager patterns;
  private List<Token> tokens;

  public Lexer(String code) {
    this.code = code;
    this.currentPosition = new LexicalRange(0, 0, 0);
    this.tokens = new ArrayList<>();
    this.patterns = new PatternManager();
  }

  public List<Token> getTokens() {
    return tokens;
    // Tokens
  }

  private void advancePosition(int length) {
    for (int i = 0; i < length; i++) {
      char c = code.charAt(currentPosition.getOffset());
      currentPosition =
          new LexicalRange(
              currentPosition.getOffset() + 1,
              c == '\n' ? currentPosition.getLine() + 1 : currentPosition.getLine(),
              c == '\n' ? 0 : currentPosition.getColumn() + 1);
    }
  }

  private Optional<Character> getCharAt(LexicalRange position) {
    if (position.getOffset() >= code.length()) {
      return Optional.empty();
    }
    return Optional.of(code.charAt(position.getOffset()));
  }

  /* Turns the code into a list of tokens */
  public void tokenize() {
    while (currentPosition.getOffset() < code.length()) {
      /* Gets the current character */
      Optional<Character> currentChar = getCharAt(currentPosition);

      /* Checks limit has not been reached */
      if (currentChar.isEmpty()) {
        break;
      }
      Character c = currentChar.get();

      /* Skips whitespaces */
      if (skipWhitespace(c)) continue;

      boolean matched = false;

      /* Compares the string with the token patterns */
      for (Map.Entry<Pattern, TokenType> entry : patterns.getPatterns().entrySet()) {
        Pattern pattern = entry.getKey();
        TokenType type = entry.getValue();

        /* Gets the substring from the current position, leaving outside what has already been matched */
        String sub = code.substring(currentPosition.getOffset());

        Matcher matcher = pattern.matcher(sub);

        /* Checks if the prefix matches the pattern */
        if (matcher.lookingAt()) {

          /* Gets the part of the code that matched the pattern */
          String tokenValue = matcher.group();

          /* If the token is a keyword, it must be a whole word */
          if (type == TokenType.KEYWORD) {
            if (!isWholeWordMatch(tokenValue)) {
              continue;
            }
          }

          /* Creates the token and adds it to the list */
          createToken(tokenValue, type);

          /* Advances the position */
          advancePosition(tokenValue.length());
          matched = true;
          break;
        }
      }

      /* If no pattern matched, it is an invalid token */
      if (!matched) {
        throw new RuntimeException("Invalid token: " + currentChar.get());
      }
    }
  }

  private void createToken(String tokenValue, TokenType type) {
    LexicalRange startRange =
        new LexicalRange(
            currentPosition.getOffset(), currentPosition.getLine(), currentPosition.getColumn());
    LexicalRange endRange =
        new LexicalRange(
            currentPosition.getOffset() + tokenValue.length() - 1,
            currentPosition.getLine(),
            currentPosition.getColumn() + tokenValue.length() - 1);
    tokens.add(new Token(type, tokenValue, startRange, endRange));
  }

  private boolean skipWhitespace(Character currentChar) {
    if (Character.isWhitespace(currentChar)) {
      advancePosition(1);
      return true;
    }
    return false;
  }

  private boolean isWholeWordMatch(String tokenValue) {
    int endOffset = currentPosition.getOffset() + tokenValue.length();
    if (endOffset < code.length()) {
      char nextChar = code.charAt(endOffset);
      return !Character.isLetterOrDigit(nextChar) && nextChar != '_';
    }
    return true;
  }
}
