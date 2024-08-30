package edu;

import static edu.utils.ParserUtil.isCloseBracket;
import static edu.utils.ParserUtil.isOpenBracket;
import static edu.utils.ParserUtil.isSemicolon;

import edu.ast.ProgramNode;
import edu.ast.interfaces.StatementNode;
import edu.check.ParserVisitor;
import edu.exceptions.UnexpectedTokenException;
import edu.parsers.ParseStatement;
import edu.tokens.Token;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Parser implements Iterator<StatementNode> {

  private final Lexer lexer;

  public Parser() {
    this.lexer = null;
  }

  public Parser(Lexer lexer) {
    this.lexer = lexer;
  }

  @Override
  public boolean hasNext() {
    return lexer.hasNext();
  }

  @Override
  public StatementNode next() {
    if (!hasNext()) {
      throw new NoSuchElementException("No more tokens to parse");
    }

    List<Token> tokens = new ArrayList<>();
    int bracketBalance = 0;

    Token current;
    do {
      current = lexer.next();
      tokens.add(current);

      if (isOpenBracket(current)) {
        bracketBalance++;
      }
      if (isCloseBracket(current)) {
        bracketBalance--;
        if (bracketBalance < 0) {
          throw new UnexpectedTokenException(current);
        }
      }
    } while (!(bracketBalance == 0 && isEndOfStatement(current)));

    return ParseStatement.parse(tokens);
  }

  private static boolean isEndOfStatement(Token t) {
    return isSemicolon(t) || isCloseBracket(t);
  }

  public ProgramNode parse(List<Token> tokens) {
    return parse(tokens, false);
  }

  public ProgramNode parse(List<Token> tokens, boolean ignoreSemantics) {

    ProgramNode root = new ProgramNode();

    if (!isSemicolon(tokens.getLast())) {
      throw new RuntimeException(); // TODO
    }
    List<List<Token>> statements = split(tokens);

    for (List<Token> statement : statements) {
      StatementNode statementNode = ParseStatement.parse(statement);
      root.addStatement(statementNode);
    }

    if (ignoreSemantics) {
      return root;
    }

    // semantic analysis (would only throw exceptions)
    new ParserVisitor(root);
    return root;
  }

  private List<List<Token>> split(List<Token> tokens) {
    List<List<Token>> statements = new ArrayList<>();
    List<Token> current = new ArrayList<>();

    for (Token token : tokens) {
      current.add(token);
      if (isSemicolon(token)) {
        statements.add(new ArrayList<>(current));
        current.clear();
      }
    }

    if (!current.isEmpty()) {
      statements.add(current);
    }

    return statements;
  }
}
