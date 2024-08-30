package edu;

import edu.patterns.TokenPattern;
import edu.tokens.Token;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Lexer implements Iterator<Token> {
  private LexicalRange position;
  private final PatternManager patterns;
  private final List<Token> tokens = new ArrayList<>();
  private final Iterator<String> fileIterator;
  private String currentLine;
  private int currentPosition;

  public Lexer(Iterator<String> fileIterator, List<TokenPattern> patterns) {
    this.fileIterator = fileIterator;
    this.position = new LexicalRange(0, 0, 0);
    this.patterns = new PatternManager(patterns);
    this.currentLine = fileIterator.hasNext() ? fileIterator.next() : null;
    this.currentPosition = 0;
  }

  @Override
  public boolean hasNext() {
    // Avanzar mientras la línea actual esté vacía o contenga solo espacios en blanco
    while (currentLine != null && currentPosition >= currentLine.length()) {
      if (fileIterator.hasNext()) {
        currentLine = fileIterator.next();
        currentPosition = 0;
        position = new LexicalRange(position.getOffset(), position.getLine() + 1, 0);
      } else {
        currentLine = null;
        return false;
      }
    }

    if (currentLine == null) {
      return false;
    }

    Optional<Character> currentChar = getCharAt(currentPosition);
    if (currentChar.isEmpty()) {
      return false;
    }

    // Avanzar espacios en blanco y comprobar si se alcanzó el final de la línea o si se debe
    // avanzar a la siguiente línea
    while (skipWhitespace(currentChar.get())) {
      if (currentPosition >= currentLine.length()) {
        if (fileIterator.hasNext()) {
          currentLine = fileIterator.next();
          currentPosition = 0;
          position = new LexicalRange(position.getOffset(), position.getLine() + 1, 0);
        } else {
          currentLine = null;
          return false;
        }
      }
      currentChar = getCharAt(currentPosition);
      if (currentChar.isEmpty()) {
        return false;
      }
    }

    return true;
  }

  private boolean skipWhitespace(Character currentChar) {
    boolean skipped = false;
    while (currentChar != null
        && (Character.isWhitespace(currentChar) || currentChar == '\n' || currentChar == '\r')) {
      advancePosition(1);
      currentChar = getCharAt(currentPosition).orElse(null);
      skipped = true;
    }
    return skipped;
  }

  @Override
  public Token next() {
    if (currentLine == null) {
      throw new RuntimeException("No more tokens available.");
    }

    String sub = currentLine.substring(currentPosition);

    Optional<Token> token = patterns.matches(sub, position);

    if (token.isEmpty()) {
      throw new RuntimeException("Invalid token: " + sub);
    } else {
      Token t = token.get();
      advancePosition(t.getEnd().getOffset() - t.getStart().getOffset() + 1);

      if (currentPosition >= currentLine.length()) {
        if (fileIterator.hasNext()) {
          currentLine = fileIterator.next();
          currentPosition = 0;
          position = new LexicalRange(position.getOffset(), position.getLine() + 1, 0);
        } else {
          currentLine = null;
        }
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

  public void getTokens(Stream.Builder<Token> tokensStream) {
    while (hasNext()) {
      tokensStream.accept(next());
    }
  }

  private void advancePosition(int length) {
    position =
        new LexicalRange(
            position.getOffset() + length, position.getLine(), position.getColumn() + length);
    currentPosition += length;
  }

  private Optional<Character> getCharAt(int position) {
    if (currentLine != null && position < currentLine.length()) {
      return Optional.of(currentLine.charAt(position));
    }
    return Optional.empty();
  }
}
