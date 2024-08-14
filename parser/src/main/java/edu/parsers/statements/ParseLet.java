package edu.parsers.statements;

import static edu.utils.ParserUtil.isKeyword;

import edu.Token;
import edu.ast.expressions.IdentifierNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.Kind;
import edu.ast.statements.Type;
import edu.ast.statements.VariableDeclarationNode;
import edu.parsers.ParseExpression;
import edu.parsers.StatementParser;
import edu.parsers.expressions.ParseIdentifier;
import edu.utils.KindProvider;
import edu.utils.LexicalRange;
import edu.utils.TypeProvider;
import java.util.List;

public class ParseLet implements StatementParser {
  /*
   * Expects a ';' at the end
   */

  @Override
  public StatementNode parse(List<Token> tokens) {
    if (!isXStatement(tokens)) {
      throw new RuntimeException(); // Should never happen
    }

    LexicalRange start = tokens.getFirst().getStart();
    LexicalRange end = tokens.getLast().getEnd();

    IdentifierNode identifier = new ParseIdentifier().parse(tokens.get(1));

    String syntax = tokens.get(2).getContent();
    if (!syntax.equals(":")) {
      throw new RuntimeException(); // TODO()
    }

    String typeString = tokens.get(3).getContent();
    Type type = TypeProvider.getType(typeString);

    if (isNotInitialized(tokens)) {
      return new VariableDeclarationNode(start, end, identifier, type, Kind.LET, null);
    }

    ExpressionNode init = ParseExpression.parse(tokens.subList(5, tokens.size() - 1));

    return new VariableDeclarationNode(start, end, identifier, type, Kind.LET, init);
  }

  @Override
  public boolean isXStatement(List<Token> tokens) {
    String keyword = tokens.getFirst().getContent();
    return isKeyword(tokens, 0) && KindProvider.getKind(keyword) == Kind.LET;
  }

  private boolean isNotInitialized(List<Token> tokens) {
    return tokens.size() == 5;
  }
}
