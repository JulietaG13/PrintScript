package lexer;

import utils.LexicalRange;

import java.util.*;
import java.util.regex.Pattern;

public class LexerContext {
  private final String code;
  private LexicalRange currentPosition;
  Map<Pattern, TokenType> patterns;
  private List<Token> tokens;

  public LexerContext(String code) {
    this.code = code;
    this.currentPosition = new LexicalRange(0, 0, 0);
    this.tokens = new ArrayList<>();
    this.patterns = new HashMap<>();
    createPatterns();
  }

  public List<Token> getTokens() {
    return tokens;
  }

  public void addPattern(Pattern pattern, TokenType type) {
    patterns.put(pattern, type);
  }

  private void advancePosition(int length) {
    for (int i = 0; i < length; i++) {
      char c = code.charAt(currentPosition.getOffset());
      currentPosition = new LexicalRange(
        currentPosition.getOffset() + 1,
        c == '\n' ? currentPosition.getLine() + 1 : currentPosition.getLine(),
        c == '\n' ? 0 : currentPosition.getColumn() + 1
      );
    }
  }

  public void createPatterns(){
    addPattern(Pattern.compile("let|String|Number"), TokenType.KEYWORD);  // Keywords
    addPattern(Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*"), TokenType.IDENTIFIER); // Identifier
    addPattern(Pattern.compile("[0-9]+"), TokenType.INTEGER_LITERAL); // Integer literal
    addPattern(Pattern.compile("\"[^\"]*\""), TokenType.STRING_LITERAL); // String literal
    addPattern(Pattern.compile("[+\\-*/=%]"), TokenType.OPERATOR); // Operators
    addPattern(Pattern.compile("[;]"), TokenType.SYNTAX); // syntax
  }

  public Optional<Character> getCharAt(LexicalRange position){
    if (position.getOffset() >= code.length()) {
      return Optional.empty();
    }
    return Optional.of(code.charAt(position.getOffset()));
  }

  public void tokenize(){
    while (currentPosition.getOffset() < code.length()) {
      Optional<Character> currentChar = getCharAt(currentPosition);
      if (Character.isWhitespace(currentChar.get())) {
        advancePosition(1);
        continue;
      }
      LexicalRange startPosition = new LexicalRange(currentPosition.getOffset(), currentPosition.getLine(), currentPosition.getColumn());
      StringBuilder result = new StringBuilder();
        while (currentChar.isPresent() && !Character.isWhitespace(currentChar.get())) {
        result.append(currentChar.get());
        currentPosition = new LexicalRange(currentPosition.getOffset() + 1, currentPosition.getLine(), currentPosition.getColumn() + 1);
        currentChar = getCharAt(currentPosition);
      }
      Optional<Token> token = match(result.toString(), startPosition);
      if (token.isPresent()) {
        tokens.add(token.get());
      } else {
        throw new RuntimeException("Invalid token: " + result.toString());
      }
    }
  }

  public Optional<Token> match(String string, LexicalRange startPosition ) {
    for (Map.Entry<Pattern, TokenType> entry : patterns.entrySet()) {
      if (entry.getKey().matcher(string).matches()) {
        Token token = new Token(entry.getValue(), string, startPosition, currentPosition);
        return Optional.of(token);
      }
    }
    return Optional.empty();
  }

}

