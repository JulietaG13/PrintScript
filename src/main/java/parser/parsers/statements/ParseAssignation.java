package parser.parsers.statements;

import parser.ast.expressions.IdentifierNode;
import parser.ast.interfaces.ExpressionNode;
import parser.ast.interfaces.StatementNode;
import parser.ast.statements.AssignmentNode;
import lexer.Token;
import parser.parsers.ParseExpression;
import parser.parsers.StatementParser;
import parser.parsers.expressions.ParseIdentifier;
import lexer.utils.LexicalRange;

import java.util.List;

import static parser.utils.ParserUtil.*;

public class ParseAssignation implements StatementParser {

  @Override
  public StatementNode parse(List<Token> tokens) {
    LexicalRange start = tokens.getFirst().getStart();
    LexicalRange end = tokens.getFirst().getEnd();

    String operator = tokens.get(1).getContent();
    IdentifierNode id = new ParseIdentifier().parse(tokens.getFirst());

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

  @Override
  public boolean isXStatement(List<Token> tokens) {
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
