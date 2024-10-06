package edu.parsers.statements;

import static edu.utils.ParserUtil.isColon;
import static edu.utils.ParserUtil.isKeyword;
import static edu.utils.ParserUtil.isSemicolon;

import edu.LexicalRange;
import edu.Parser;
import edu.ast.expressions.IdentifierNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.Kind;
import edu.ast.statements.Type;
import edu.ast.statements.VariableDeclarationNode;
import edu.exceptions.UnexpectedTokenException;
import edu.parsers.StatementParser;
import edu.parsers.expressions.ParseIdentifier;
import edu.tokens.Token;
import edu.utils.KindProvider;
import edu.utils.TypeProvider;
import java.util.List;

public class ParseConst implements StatementParser {
  /*
   * Expects a ';' at the end
   */

  @Override
  public StatementNode parse(List<Token> tokens, Parser parser) {
    if (!isXstatement(tokens)) {
      throw new RuntimeException(); // Should never happen
    }

    LexicalRange start = tokens.getFirst().getStart();
    LexicalRange end = tokens.getLast().getEnd();

    IdentifierNode identifier = new ParseIdentifier().parse(tokens.get(1), parser);

    Token syntax = tokens.get(2);
    if (!isColon(syntax)) {
      throw new UnexpectedTokenException(syntax, ":");
    }

    String typeString = tokens.get(3).getContent();
    Type type = TypeProvider.getType(typeString, tokens.get(3).getStart());

    if (isNotInitialized(tokens)) { // TODO (is it allowed?)
      return new VariableDeclarationNode(start, end, identifier, type, Kind.CONST, null);
    }

    ExpressionNode init = parser.parseExpression(tokens.subList(5, tokens.size() - 1));

    return new VariableDeclarationNode(start, end, identifier, type, Kind.CONST, init);
  }

  @Override
  public boolean isXstatement(List<Token> tokens) {
    String keyword = tokens.getFirst().getContent();
    Kind kind;
    try {
      kind = KindProvider.getKind(keyword, tokens.getFirst().getStart());
    } catch (Exception e) {
      return false;
    }
    return isKeyword(tokens, 0) && kind == Kind.CONST;
  }

  @Override
  public boolean isFinished(List<Token> tokens, Token next) {
    return isSemicolon(tokens.getLast());
  }

  private boolean isNotInitialized(List<Token> tokens) {
    return tokens.size() == 5;
  }
}
