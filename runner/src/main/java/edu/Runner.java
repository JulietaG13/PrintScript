package edu;

import edu.tokens.Token;

public class Runner {

  private final Lexer lexer;
  private final Parser parser;

  public Runner(String code) {
    this.lexer = new Lexer(code);
    this.parser = new Parser();
  }

  public void run() {
    while (lexer.hasNext()) {
      Token token = lexer.next();
      // parser.addToken(token);
    }
    // parser.getProgramNode();
  }
}
