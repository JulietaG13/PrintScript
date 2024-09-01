package edu;

import static edu.TokenProvider.getCloseParen;
import static edu.TokenProvider.getComma;
import static edu.TokenProvider.getIdentifier;
import static edu.TokenProvider.getLiteral;
import static edu.TokenProvider.getOpenParen;
import static edu.TokenProvider.getOperator;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ast.expressions.BinaryExpressionNode;
import edu.ast.expressions.CallExpressionNode;
import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.interfaces.ExpressionNode;
import edu.tokens.Token;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BinaryExpressionTest {

  @Test
  public void simpleLiterals() { // 3 + 44
    String op = "+";
    int left = 3;
    int right = 44;
    List<Token> input = List.of(getLiteral(left), getOperator(op), getLiteral(right));

    ExpressionNode exp = new Parser().parseExpression(input);

    assert exp instanceof BinaryExpressionNode;
    BinaryExpressionNode binaryExp = (BinaryExpressionNode) exp;

    assertEquals(op, binaryExp.operator());

    assert binaryExp.left() instanceof LiteralNumberNode;
    assert binaryExp.right() instanceof LiteralNumberNode;

    LiteralNumberNode leftExp = (LiteralNumberNode) binaryExp.left();
    LiteralNumberNode rightExp = (LiteralNumberNode) binaryExp.right();

    assertEquals(left, leftExp.value());
    assertEquals(right, rightExp.value());
  }

  @Test
  public void simpleLiteralsExtraParens() { // (3 + 44)
    String op = "+";
    int left = 3;
    int right = 44;
    List<Token> input =
        List.of(
            getOpenParen(), getLiteral(left), getOperator(op), getLiteral(right), getCloseParen());

    ExpressionNode exp = new Parser().parseExpression(input);

    assert exp instanceof BinaryExpressionNode;
    BinaryExpressionNode binaryExp = (BinaryExpressionNode) exp;

    assertEquals(op, binaryExp.operator());

    assert binaryExp.left() instanceof LiteralNumberNode;
    assert binaryExp.right() instanceof LiteralNumberNode;

    LiteralNumberNode leftExp = (LiteralNumberNode) binaryExp.left();
    LiteralNumberNode rightExp = (LiteralNumberNode) binaryExp.right();

    assertEquals(left, leftExp.value());
    assertEquals(right, rightExp.value());
  }

  @Test
  public void sumThreeLiterals() { // 3 + 44 + 4.5
    String op = "+";
    int fst = 3;
    int sec = 44;
    double thr = 4.5;
    List<Token> input =
        List.of(
            getLiteral(fst), getOperator(op), getLiteral(sec), getOperator(op), getLiteral(thr));

    ExpressionNode exp = new Parser().parseExpression(input);
    System.out.println(exp);

    assert exp instanceof BinaryExpressionNode;
    BinaryExpressionNode binaryExp = (BinaryExpressionNode) exp;

    assertEquals(op, binaryExp.operator());

    assert binaryExp.left() instanceof LiteralNumberNode;
    assert binaryExp.right() instanceof BinaryExpressionNode;

    LiteralNumberNode leftExp = (LiteralNumberNode) binaryExp.left();
    BinaryExpressionNode rightExp = (BinaryExpressionNode) binaryExp.right();

    assertEquals(fst, leftExp.value());

    assert rightExp.left() instanceof LiteralNumberNode;
    assert rightExp.right() instanceof LiteralNumberNode;

    LiteralNumberNode rightLeftExp = (LiteralNumberNode) rightExp.left();
    LiteralNumberNode rightRightExp = (LiteralNumberNode) rightExp.right();

    assertEquals(sec, rightLeftExp.value());
    assertEquals(thr, rightRightExp.value());
  }

  @Test
  public void manyLiterals() { // a * (b + c) + d * e
    String sum = "+";
    String prod = "*";
    int a = 1;
    int b = 2;
    int c = 3;
    int d = 4;
    int e = 5;

    List<Token> input =
        List.of(
            getLiteral(a),
            getOperator(prod),
            getOpenParen(),
            getLiteral(b),
            getOperator(sum),
            getLiteral(c),
            getCloseParen(),
            getOperator(sum),
            getLiteral(d),
            getOperator(prod),
            getLiteral(e));

    ExpressionNode exp = new Parser().parseExpression(input);
    System.out.println(exp);

    assert exp instanceof BinaryExpressionNode;
    BinaryExpressionNode binaryExp = (BinaryExpressionNode) exp;

    assertEquals(sum, binaryExp.operator());

    assert binaryExp.left() instanceof BinaryExpressionNode;
    assert binaryExp.right() instanceof BinaryExpressionNode;

    BinaryExpressionNode leftExp = (BinaryExpressionNode) binaryExp.left(); // a * (b + c)
    BinaryExpressionNode rightExp = (BinaryExpressionNode) binaryExp.right(); // d * e

    // a * (b + c)
    assert leftExp.left() instanceof LiteralNumberNode;
    assert leftExp.right() instanceof BinaryExpressionNode;
    assertEquals(prod, leftExp.operator());

    LiteralNumberNode literalA = (LiteralNumberNode) leftExp.left();
    BinaryExpressionNode expressionSumbc = (BinaryExpressionNode) leftExp.right();

    assertEquals(a, literalA.value());

    assert expressionSumbc.left() instanceof LiteralNumberNode;
    assert expressionSumbc.right() instanceof LiteralNumberNode;
    assertEquals(sum, expressionSumbc.operator());

    LiteralNumberNode literalB = (LiteralNumberNode) expressionSumbc.left();
    LiteralNumberNode literalC = (LiteralNumberNode) expressionSumbc.right();

    assertEquals(b, literalB.value());
    assertEquals(c, literalC.value());

    // d * e
    assert rightExp.left() instanceof LiteralNumberNode;
    assert rightExp.right() instanceof LiteralNumberNode;
    assertEquals(prod, rightExp.operator());

    LiteralNumberNode literalD = (LiteralNumberNode) rightExp.left();
    LiteralNumberNode literalE = (LiteralNumberNode) rightExp.right();

    assertEquals(d, literalD.value());
    assertEquals(e, literalE.value());
  }

  @Test
  public void sumFunctions() { // f1(var1, 321 + 256) - f2()
    String op = "-";
    String f1 = "func1";
    String arg1 = "var1";
    int n1 = 321;
    int n2 = 256;
    String f2 = "func2";

    List<Token> input =
        List.of(
            getIdentifier(f1),
            getOpenParen(),
            getIdentifier(arg1),
            getComma(),
            getLiteral(n1),
            getOperator(op),
            getLiteral(n2),
            getCloseParen(),
            getOperator(op),
            getIdentifier(f2),
            getOpenParen(),
            getCloseParen());

    ExpressionNode exp = new Parser().parseExpression(input);
    System.out.println(exp);

    assert exp instanceof BinaryExpressionNode;
    BinaryExpressionNode binaryExp = (BinaryExpressionNode) exp;

    assertEquals(op, binaryExp.operator());

    assert binaryExp.left() instanceof CallExpressionNode;
    assert binaryExp.right() instanceof CallExpressionNode;

    CallExpressionNode leftExp = (CallExpressionNode) binaryExp.left(); // f1(var1, 321 + 256)
    CallExpressionNode rightExp = (CallExpressionNode) binaryExp.right(); // f2()

    assertEquals(f1, leftExp.callee().name());
    assertEquals(f2, rightExp.callee().name());

    assertEquals(2, leftExp.args().size());
    assertEquals(0, rightExp.args().size());

    List<ExpressionNode> args = leftExp.args();
    assert args.get(0) instanceof IdentifierNode;
    assert args.get(1) instanceof BinaryExpressionNode;

    IdentifierNode id = (IdentifierNode) args.getFirst();
    assertEquals(arg1, id.name());

    BinaryExpressionNode sumExp = (BinaryExpressionNode) args.get(1);
    assert sumExp.left() instanceof LiteralNumberNode;
    assert sumExp.right() instanceof LiteralNumberNode;
    assertEquals(op, sumExp.operator());

    assertEquals(n1, ((LiteralNumberNode) sumExp.left()).value());
    assertEquals(n2, ((LiteralNumberNode) sumExp.right()).value());
  }
}
