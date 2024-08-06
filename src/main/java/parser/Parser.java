package parser;

import ast.ProgramNode;
import ast.interfaces.StatementNode;
import lexer.Token;
import parser.parsers.ParseStatement;

import java.util.ArrayList;
import java.util.List;

import static parser.utils.ParserUtil.isEndLine;

public class Parser {

  private ProgramNode root;

  public ProgramNode parse(List<Token> tokens) {

    root = new ProgramNode();

    if (!isEndLine(tokens.getLast())) {
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
      if (isEndLine(token)) {
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