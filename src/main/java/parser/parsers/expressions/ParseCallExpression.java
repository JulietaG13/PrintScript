package parser.parsers.expressions;

import ast.expressions.CallExpressionNode;
import ast.expressions.IdentifierNode;
import ast.interfaces.ExpressionNode;
import lexer.Token;
import parser.parsers.ParseExpression;
import utils.LexicalRange;

import java.util.ArrayList;
import java.util.List;

import static parser.utils.ParserUtil.*;

public class ParseCallExpression {
  /*
   * Does not expect a ';' at the end
   */

  public static CallExpressionNode parse(List<Token> tokens) {
    String name = tokens.getFirst().getContent();
    LexicalRange start = tokens.getFirst().getStart();
    LexicalRange end = tokens.getLast().getEnd();
    // Identifier + Syntax + ... + Syntax

    IdentifierNode identifier = new IdentifierNode(
        start,
        end,
        name
    );

    List<List<Token>> expressions = separateArgs(tokens.subList(2, tokens.size() - 1));
    List<ExpressionNode> args = new ArrayList<>();

    for (List<Token> expression : expressions) {
      args.add(ParseExpression.parse(expression));
    }

    return new CallExpressionNode(
        start,
        end,
        identifier,
        args
    );
  }

  private static List<List<Token>> separateArgs(List<Token> tokens) {
    List<List<Token>> args = new ArrayList<>();
    List<Token> current = new ArrayList<>();

    for (Token token : tokens) {
      if (isArgSeparator(token)) {
        if (current.isEmpty()) {
          throw new RuntimeException(); // TODO(double commas / empty arg)
        }
        args.add(new ArrayList<>(current));
        current.clear();
      } else {
        current.add(token);
      }
    }

    if (!current.isEmpty()) {
      args.add(current);
    }
    return args;
  }
}
