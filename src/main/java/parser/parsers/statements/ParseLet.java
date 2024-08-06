package parser.parsers.statements;

import ast.expressions.IdentifierNode;
import ast.interfaces.ExpressionNode;
import ast.statements.Kind;
import ast.statements.Type;
import ast.statements.VariableDeclarationNode;
import parser.parsers.ParseExpression;
import parser.utils.KindProvider;
import parser.utils.TypeProvider;
import lexer.Token;
import utils.LexicalRange;

import java.util.List;

import static parser.utils.ParserUtil.getIdentifier;
import static parser.utils.ParserUtil.isKeyword;

public class ParseLet {
  /*
  * Expects a ';' at the end
  */

  public static VariableDeclarationNode parse(List<Token> tokens) {

    String keyword = tokens.getFirst().getContent();
    if (!isKeyword(tokens, 0) || KindProvider.getKind(keyword) != Kind.LET) {
      throw new RuntimeException(); // TODO(should never happen)
    }

    LexicalRange start = tokens.getFirst().getStart();
    LexicalRange end = tokens.getLast().getEnd();

    IdentifierNode identifier = getIdentifier(tokens.get(1));

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

  private static boolean isNotInitialized(List<Token> tokens) {
    return tokens.size() == 5;
  }
}
