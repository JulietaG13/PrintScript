package edu;

import edu.exceptions.InvalidTokenException;
import edu.finders.PatternManager;
import edu.tokens.Token;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Lexer implements Iterator<Token> {
  private LexicalRange position;
  private final PatternManager patternManager;
  private final List<Token> tokens = new ArrayList<>();
  private final Reader reader;
  private String currentLine;

  public Lexer(Reader reader, PatternManager patternManager) {
    this.reader = reader; /* The reader will provide lines of code */
    this.position = new LexicalRange(0, 0, 0); /* The position in the overall code */
    this.currentLine = reader.hasNext() ? reader.next() : null; /* The current line of code */
    this.patternManager = patternManager; /* The pattern manager will find a match given a string */
  }

  /* Checks if there is any possible token left */
  @Override
  public boolean hasNext() {
    return !noMoreTokensLeftToRead();
  }

  /* Returns the next token */
  @Override
  public Token next() {
    /* If there are no more tokens to read, throw an exception */
    if (!hasNext()) {
      throw new NoSuchElementException("No more code to read.");
    }
    String sub = currentLine.substring(position.getColumn());
    Optional<Token> token = patternManager.findMatch(sub, position);
    if (token.isEmpty()) {
      throw new InvalidTokenException(sub, position);
    } else {
      Token t = token.get();
      advancePosition(t.getEnd().getOffset() - t.getStart().getOffset() + 1);
      if (position.getColumn() >= currentLine.length()) {
        skipToNextLine();
      }
      return t;
    }
  }

  /* Checks if there is more code to transform to tokens */
  private boolean noMoreTokensLeftToRead() {
    /* Advance to the next non-empty line */
    advanceToTheNextReadableLine();
    /* Checks if there are no more lines */
    if (!fileNotFinished()) {
      return true;
    }
    Optional<Character> currentChar = getCharAt(position.getColumn());
    if (currentChar.isEmpty()) {
      return true;
    }
    /* Advance all the whitespace characters */
    while (skipWhitespace(currentChar.get())) {
      if (fileNotFinished() && reachedEndOfLine()) {
        skipToNextLine();
      }
      /* Checks if there are no more lines */
      if (!fileNotFinished()) {
        return true;
      }
      currentChar = getCharAt(position.getColumn());
      if (currentChar.isEmpty()) {
        return true;
      }
    }
    return false;
  }

  private void advanceToTheNextReadableLine() {
    while (fileNotFinished() && reachedEndOfLine()) {
      skipToNextLine();
    }
  }

  /* If the line is null then there are no more lines */
  private boolean fileNotFinished() {
    return currentLine != null;
  }

  /* Skip to the next line */
  private void skipToNextLine() {
    if (reader.hasNext()) {
      currentLine = reader.next();
      position = new LexicalRange(position.getOffset(), position.getLine() + 1, 0);
    } else {
      currentLine = null;
    }
  }

  /* Checks if the current position is at the end of the line */
  private boolean reachedEndOfLine() {
    return position.getColumn() >= currentLine.length();
  }

  private boolean skipWhitespace(Character currentChar) {
    boolean skipped = false;
    while (currentChar != null
        && (Character.isWhitespace(currentChar) || currentChar == '\n' || currentChar == '\r')) {
      advancePosition(1);
      currentChar = getCharAt(position.getColumn()).orElse(null);
      skipped = true;
    }
    return skipped;
  }

  private void advancePosition(int length) {
    position =
        new LexicalRange(
            position.getOffset() + length, position.getLine(), position.getColumn() + length);
  }

  private Optional<Character> getCharAt(int position) {
    if (currentLine != null && position < currentLine.length()) {
      return Optional.of(currentLine.charAt(position));
    }
    return Optional.empty();
  }

  /* ----------------------------------------------------------------- */

  /* Methods used for testing */
  public void tokenize() {
    while (hasNext()) {
      tokens.add(next());
    }
  }

  public List<Token> getTokens() {
    return tokens;
  }
}
