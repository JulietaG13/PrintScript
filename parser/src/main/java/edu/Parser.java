package edu;

import static edu.utils.ParserUtil.isCloseBracket;
import static edu.utils.ParserUtil.isEndOfStatement;
import static edu.utils.ParserUtil.isOpenBracket;
import static edu.utils.ParserUtil.isSemicolon;

import edu.ast.ProgramNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;
import edu.check.ParserVisitor;
import edu.exceptions.InvalidExpressionException;
import edu.exceptions.InvalidStatementException;
import edu.exceptions.UnexpectedTokenException;
import edu.parsers.ExpressionParser;
import edu.parsers.StatementParser;
import edu.parsers.expressions.ParseBinaryExpression;
import edu.parsers.expressions.ParseCallExpression;
import edu.parsers.expressions.ParseIdentifier;
import edu.parsers.expressions.ParseLiteral;
import edu.parsers.statements.ParseAssignation;
import edu.parsers.statements.ParseLet;
import edu.parsers.statements.ParseStatementExpression;
import edu.tokens.Token;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Parser implements Iterator<StatementNode> {

  private final Lexer lexer;
  private final List<StatementParser> statementParsers;
  private final List<ExpressionParser> expressionParsers;

  private Token nextToken;
  private ParserVisitor visitor = new ParserVisitor();

  public Parser() {
    this.lexer = null;
    this.statementParsers =
        List.of(new ParseAssignation(), new ParseLet(), new ParseStatementExpression());
    this.expressionParsers =
        List.of(
            new ParseBinaryExpression(),
            new ParseCallExpression(),
            new ParseIdentifier(),
            new ParseLiteral());
  }

  public Parser(
      Lexer lexer,
      List<StatementParser> statementParsers,
      List<ExpressionParser> expressionParsers) {
    this.lexer = lexer;
    this.statementParsers = statementParsers;
    this.expressionParsers = expressionParsers;

    if (lexer.hasNext()) {
      nextToken = lexer.next();
    }
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
      current = nextToken;
      tokens.add(current);
      nextToken = lexer.next();

      if (isOpenBracket(current)) {
        bracketBalance++;
      }
      if (isCloseBracket(current)) {
        bracketBalance--;
        if (bracketBalance < 0) {
          throw new UnexpectedTokenException(current);
        }
      }
    } while (lexer.hasNext() && !(bracketBalance == 0 && isFinished(tokens, nextToken)));

    if (!lexer.hasNext()) {
      if (!isEndOfStatement(nextToken)) {
        throw new RuntimeException("statement not finished");
      }
      tokens.add(nextToken);
    }

    StatementNode statement = parseStatement(tokens);

    statement.accept(visitor);

    return statement;
  }

  private boolean isFinished(List<Token> currentTokens, Token next) {
    for (StatementParser parser : statementParsers) {
      if (parser.isFinished(currentTokens, next)) {
        return true;
      }
    }
    return false;
  }

  public ExpressionNode parseExpression(List<Token> tokens) {
    for (ExpressionParser parser : expressionParsers) {
      if (parser.isXexpression(tokens)) {
        return parser.parse(tokens, this);
      }
    }
    throw new InvalidExpressionException(tokens.getFirst(), tokens.getLast());
  }

  public StatementNode parseStatement(List<Token> tokens) {
    for (StatementParser parser : statementParsers) {
      if (parser.isXstatement(tokens)) {
        return parser.parse(tokens, this);
      }
    }

    throw new InvalidStatementException(tokens.getFirst(), tokens.getLast());
  }

  /*---------------------------------------------------------------------------*/

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
      StatementNode statementNode = parseStatement(statement);
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
