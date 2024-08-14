package edu;

import edu.ast.ProgramNode;
import edu.ast.interfaces.StatementNode;
import edu.parsers.ParseStatement;
import edu.utils.ParserUtil;
import java.util.ArrayList;
import java.util.List;

public class Parser {

  private ProgramNode root;

  public ProgramNode parse(List<Token> tokens) {

    root = new ProgramNode();

    if (!ParserUtil.isEndLine(tokens.getLast())) {
      throw new RuntimeException(); // TODO
    }
    List<List<Token>> statements = split(tokens);

    for (List<Token> statement : statements) {
      StatementNode statementNode = ParseStatement.parse(statement);
      root.addStatement(statementNode);
    }

    return root;
  }

  private List<List<Token>> split(List<Token> tokens) {
    List<List<Token>> statements = new ArrayList<>();
    List<Token> current = new ArrayList<>();

    for (Token token : tokens) {
      current.add(token);
      if (ParserUtil.isEndLine(token)) {
        statements.add(new ArrayList<>(current));
        current.clear();
      }
    }

    if (!current.isEmpty()) {
      statements.add(current);
    }

    return statements;
  }

  public ProgramNode getRoot() {
    return root;
  }
}
