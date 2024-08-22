package edu;

import static edu.TokenProvider.getCloseParen;
import static edu.TokenProvider.getComma;
import static edu.TokenProvider.getIdentifier;
import static edu.TokenProvider.getLiteral;
import static edu.TokenProvider.getOpenParen;
import static edu.TokenProvider.getSemicolon;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ast.ProgramNode;
import edu.ast.expressions.CallExpressionNode;
import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.ExpressionStatementNode;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FunctionTest {

  @Test
  public void noArgs() {
    String func = "println";

    List<Token> input =
        List.of(getIdentifier(func), getOpenParen(), getCloseParen(), getSemicolon());

    ProgramNode root = new Parser().parse(input, true);
    List<StatementNode> body = root.getBody();

    assertEquals(1, body.size());
    assert body.getFirst() instanceof ExpressionStatementNode;

    ExpressionStatementNode exp = (ExpressionStatementNode) body.getFirst();
    assert exp.expression() instanceof CallExpressionNode;

    CallExpressionNode call = (CallExpressionNode) exp.expression();
    assertEquals(0, call.args().size());
    assertEquals(func, call.callee().name());
  }

  @Test
  public void oneArg() {
    String func = "println";
    String arg = "hola";

    List<Token> input =
        List.of(
            getIdentifier(func), getOpenParen(), getLiteral(arg), getCloseParen(), getSemicolon());

    ProgramNode root = new Parser().parse(input, true);
    List<StatementNode> body = root.getBody();

    assertEquals(1, body.size());
    assert body.getFirst() instanceof ExpressionStatementNode;

    ExpressionStatementNode exp = (ExpressionStatementNode) body.getFirst();
    assert exp.expression() instanceof CallExpressionNode;

    CallExpressionNode call = (CallExpressionNode) exp.expression();
    assertEquals(1, call.args().size());
    assertEquals(func, call.callee().name());

    assert call.args().getFirst() instanceof LiteralStringNode;
    LiteralStringNode lit = (LiteralStringNode) call.args().getFirst();
    assertEquals(arg, lit.value());
  }

  @Test
  public void twoArgs() {
    String func = "println";
    String arg1 = "hola";
    Double arg2 = 123.0;

    List<Token> input =
        List.of(
            getIdentifier(func),
            getOpenParen(),
            getLiteral(arg1),
            getComma(),
            getLiteral(arg2),
            getCloseParen(),
            getSemicolon());

    ProgramNode root = new Parser().parse(input, true);
    List<StatementNode> body = root.getBody();

    assertEquals(1, body.size());
    assert body.getFirst() instanceof ExpressionStatementNode;

    ExpressionStatementNode exp = (ExpressionStatementNode) body.getFirst();
    assert exp.expression() instanceof CallExpressionNode;

    CallExpressionNode call = (CallExpressionNode) exp.expression();
    assertEquals(2, call.args().size());
    assertEquals(func, call.callee().name());

    assert call.args().getFirst() instanceof LiteralStringNode;
    LiteralStringNode lit1 = (LiteralStringNode) call.args().getFirst();
    assertEquals(arg1, lit1.value());

    assert call.args().get(1) instanceof LiteralNumberNode;
    LiteralNumberNode lit2 = (LiteralNumberNode) call.args().get(1);
    assertEquals(arg2, lit2.value());
  }

  @Test
  public void varAsArg() {
    String func = "println";
    String var = "var";

    List<Token> input =
        List.of(
            getIdentifier(func),
            getOpenParen(),
            getIdentifier(var),
            getCloseParen(),
            getSemicolon());

    ProgramNode root = new Parser().parse(input, true);
    List<StatementNode> body = root.getBody();

    assertEquals(1, body.size());
    assert body.getFirst() instanceof ExpressionStatementNode;

    ExpressionStatementNode exp = (ExpressionStatementNode) body.getFirst();
    assert exp.expression() instanceof CallExpressionNode;

    CallExpressionNode call = (CallExpressionNode) exp.expression();
    assertEquals(1, call.args().size());
    assertEquals(func, call.callee().name());

    assert call.args().getFirst() instanceof IdentifierNode;
    IdentifierNode id = (IdentifierNode) call.args().getFirst();
    assertEquals(var, id.name());
  }

  @Test
  public void functionAsArg() {
    String func = "println";
    String nestedFunc = "foo";

    List<Token> input =
        List.of(
            getIdentifier(func),
            getOpenParen(),
            getIdentifier(nestedFunc),
            getOpenParen(),
            getCloseParen(),
            getCloseParen(),
            getSemicolon());

    ProgramNode root = new Parser().parse(input, true);
    List<StatementNode> body = root.getBody();

    assertEquals(1, body.size());
    assert body.getFirst() instanceof ExpressionStatementNode;

    ExpressionStatementNode exp = (ExpressionStatementNode) body.getFirst();
    assert exp.expression() instanceof CallExpressionNode;

    CallExpressionNode call = (CallExpressionNode) exp.expression();
    assertEquals(1, call.args().size());
    assertEquals(func, call.callee().name());

    assert call.args().getFirst() instanceof CallExpressionNode; // TODO(check)
    CallExpressionNode call2 = (CallExpressionNode) call.args().getFirst();
    assertEquals(0, call2.args().size());
    assertEquals(nestedFunc, call2.callee().name());
  }
}
