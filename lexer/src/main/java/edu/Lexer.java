package edu;

import edu.exceptions.InvalidTokenException;
import edu.patterns.TokenPattern;
import edu.tokens.Token;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Lexer implements Iterator<Token> {
  private LexicalRange position;
  private final PatternManager patterns;
  private final List<Token> tokens = new ArrayList<>();
  private final Reader reader;
  private String currentLine;
  private int positionInLine;

  public Lexer(Reader reader, List<TokenPattern> patterns) {
    this.reader = reader;
    this.position = new LexicalRange(0, 0, 0);
    this.patterns = new PatternManager(patterns);
    this.currentLine = reader.hasNext() ? reader.next() : null;
    this.positionInLine = 0;
  }

  @Override
  public boolean hasNext() {
    if (isEndOfFile()) {
      return false;
    }
    return true;
  }

  private boolean isEndOfFile() {
    while (!lineIsEmpty() && reachedEndOfLine()) {
      skipToNextLine();
    }
    if (lineIsEmpty()) {
      return true;
    }
    Optional<Character> currentChar = getCharAt(positionInLine);
    if (currentChar.isEmpty()) {
      return true;
    }
    while (skipWhitespace(currentChar.get())) {
      if (!lineIsEmpty() && reachedEndOfLine()) {
        skipToNextLine();
      }
      if (lineIsEmpty()) {
        return true;
      }
      currentChar = getCharAt(positionInLine);
      if (currentChar.isEmpty()) {
        return true;
      }
    }
    return false;
  }

  private boolean lineIsEmpty() {
    if (currentLine == null) {
      return true;
    }
    return false;
  }

  private void skipToNextLine() {
    if (reader.hasNext()) {
      moveToNextLine();
    } else {
      currentLine = null;
    }
  }

  private void moveToNextLine() {
    currentLine = reader.next();
    positionInLine = 0;
    position = new LexicalRange(position.getOffset(), position.getLine() + 1, 0);
  }

  private boolean reachedEndOfLine() {
    return positionInLine >= currentLine.length();
  }

  private boolean skipWhitespace(Character currentChar) {
    boolean skipped = false;
    while (currentChar != null
        && (Character.isWhitespace(currentChar) || currentChar == '\n' || currentChar == '\r')) {
      advancePosition(1);
      currentChar = getCharAt(positionInLine).orElse(null);
      skipped = true;
    }
    return skipped;
  }

  @Override
  public Token next() {
    if (!hasNext()) {
      throw new NoSuchElementException("No more code to read.");
    }
    String sub = currentLine.substring(positionInLine);
    Optional<Token> token = patterns.matches(sub, position);
    if (token.isEmpty()) {
      throw new InvalidTokenException(sub, position);
    } else {
      Token t = token.get();
      advancePosition(t.getEnd().getOffset() - t.getStart().getOffset() + 1);
      if (positionInLine >= currentLine.length()) {
        skipToNextLine();
      }
      return t;
    }
  }

  public void tokenize() {
    while (hasNext()) {
      tokens.add(next());
    }
  }

  public List<Token> getTokens() {
    return tokens;
  }

  private void advancePosition(int length) {
    position =
        new LexicalRange(
            position.getOffset() + length, position.getLine(), position.getColumn() + length);
    positionInLine += length;
  }

  private Optional<Character> getCharAt(int position) {
    if (currentLine != null && position < currentLine.length()) {
      return Optional.of(currentLine.charAt(position));
    }
    return Optional.empty();
  }
}
