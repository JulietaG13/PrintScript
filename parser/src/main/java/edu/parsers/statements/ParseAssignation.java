package edu.parsers.statements;

import static edu.utils.ParserUtil.isAssign;
import static edu.utils.ParserUtil.isIdentifier;

import edu.Token;
import edu.ast.expressions.IdentifierNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.AssignmentNode;
import edu.parsers.ParseExpression;
import edu.parsers.StatementParser;
import edu.parsers.expressions.ParseIdentifier;
import edu.utils.LexicalRange;
import java.util.List;

public class ParseAssignation implements StatementParser {

  @Override
  public StatementNode parse(List<Token> tokens) {
    LexicalRange start = tokens.getFirst().getStart();
    LexicalRange end = tokens.getFirst().getEnd();

    String operator = tokens.get(1).getContent();
    IdentifierNode id = new ParseIdentifier().parse(tokens.getFirst());

    List<Token> expressionPart = tokens.subList(2, tokens.size() - 1); // id = [...] ;
    ExpressionNode expression = ParseExpression.parse(expressionPart);

    return new AssignmentNode(start, end, operator, id, expression);
  }

  @Override
  public boolean isXstatement(List<Token> tokens) {
    if (!isIdentifier(tokens, 0)) {
      return false;
    }

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
