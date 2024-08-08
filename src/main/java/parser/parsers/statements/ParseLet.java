package parser.parsers.statements;

import ast.expressions.IdentifierNode;
import ast.interfaces.ExpressionNode;
import ast.interfaces.StatementNode;
import ast.statements.Kind;
import ast.statements.Type;
import ast.statements.VariableDeclarationNode;
import common.Keyword;
import parser.parsers.ParseExpression;
import parser.parsers.StatementParser;
import parser.parsers.expressions.ParseIdentifier;
import parser.utils.KindProvider;
import parser.utils.TypeProvider;
import lexer.Token;
import utils.LexicalRange;
import java.util.List;

import static parser.utils.ParserUtil.isKeyword;

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
      return new VariableDeclarationNode(
          start,
          end,
          identifier,
          type,
          Kind.LET,
          null
      );
    }

    ExpressionNode init = ParseExpression.parse(tokens.subList(5, tokens.size() - 1));

    return new VariableDeclarationNode(
        start,
        end,
        identifier,
        type,
        Kind.LET,
        init
    );
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
