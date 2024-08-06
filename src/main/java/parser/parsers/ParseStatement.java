package parser.parsers;

import ast.expressions.CallExpressionNode;
import ast.expressions.IdentifierNode;
import ast.interfaces.ExpressionNode;
import ast.interfaces.StatementNode;
import ast.statements.AssignmentNode;
import ast.statements.ExpressionStatementNode;
import common.Keyword;
import lexer.Token;
import parser.parsers.expressions.ParseCallExpression;
import parser.parsers.statements.ParseLet;
import utils.LexicalRange;

import java.util.List;

import static common.KeywordProvider.getKeyword;
import static parser.utils.ParserUtil.*;

public class ParseStatement {
  /*
   * Expects a ';' at the end
   */

  public static StatementNode parse(List<Token> tokens) {
    Token first = tokens.getFirst();

    return switch (first.getType()) {
      case KEYWORD -> parseKeyword(tokens);
      case IDENTIFIER -> parseIdentifier(tokens);
      default -> throw new RuntimeException();  // TODO
    };
  }

  private static StatementNode parseKeyword(List<Token> tokens) {
    Token first = tokens.getFirst();
    Keyword keyword = getKeyword(first.getContent());

    return switch (keyword) {
      case LET -> ParseLet.parse(tokens);
      default -> throw new RuntimeException();  // TODO
    };
  }

  private static StatementNode parseIdentifier(List<Token> tokens) {
    // can be var (assignation) or function
    LexicalRange start = tokens.getFirst().getStart();
    LexicalRange end = tokens.getFirst().getEnd();

    if (isFunction(tokens)) {
      List<Token> callPart = tokens.subList(0, tokens.size() - 1);  // [id(...)] ;
      return new ExpressionStatementNode(
          start,
          end,
          ParseCallExpression.parse(callPart)
      );
    }

    if (isAssignation(tokens)) {
      String operator = tokens.get(1).getContent();
      IdentifierNode id = getIdentifier(tokens.getFirst());

      List<Token> expressionPart = tokens.subList(2, tokens.size() - 1);  // id = [...] ;
      ExpressionNode expression = ParseExpression.parse(expressionPart);

      return new AssignmentNode(
          start,
          end,
          operator,
          id,
          expression
      );
    }

    throw new RuntimeException(); // TODO
  }

  private static boolean isFunction(List<Token> tokens) {
    return isIdentifier(tokens, 0) && isOpenParen(tokens, 1);
  }

  private static boolean isAssignation(List<Token> tokens) {
    boolean foundAssign = isAssign(tokens.get(1));
    if (!foundAssign) {
      return false;
    }

    for (int i = 2; i < tokens.size(); i++) {
      if (isAssign(tokens.get(i))) {
        return false;
      }
    }
    return true;
  }
}
