package edu;

import static edu.TokenProvider.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ast.expressions.BinaryExpressionNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.interfaces.ExpressionNode;
import edu.parsers.ParseExpression;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BinaryExpressionTest {

  @Test
  public void simpleLiterals() {
    String op = "+";
    int left = 3;
    int right = 44;
    List<Token> input = List.of(getLiteral(left), getOperator(op), getLiteral(right));

    ExpressionNode exp = ParseExpression.parse(input);

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
  public void simpleLiteralsExtraParens() {
    String op = "+";
    int left = 3;
    int right = 44;
    List<Token> input =
        List.of(
            getOpenParen(), getLiteral(left), getOperator(op), getLiteral(right), getCloseParen());

    ExpressionNode exp = ParseExpression.parse(input);

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
  public void sumThreeLiterals() {
    String op = "+";
    int fst = 3;
    int sec = 44;
    double thr = 4.5;
    List<Token> input =
        List.of(
            getLiteral(fst), getOperator(op), getLiteral(sec), getOperator(op), getLiteral(thr));

    ExpressionNode exp = ParseExpression.parse(input);
    System.out.println(exp);

    /*
    assert exp instanceof BinaryExpressionNode;
    BinaryExpressionNode binaryExp = (BinaryExpressionNode) exp;

    assertEquals(op, binaryExp.operator());

    assert binaryExp.left() instanceof LiteralNumberNode;
    assert binaryExp.right() instanceof LiteralNumberNode;

    LiteralNumberNode leftExp = (LiteralNumberNode) binaryExp.left();
    LiteralNumberNode rightExp = (LiteralNumberNode) binaryExp.right();
    */
  }

  @Test
  public void manyLiterals() { // a * (b + c) + d * e
    String sum = "+";
    String mult = "*";
    int a = 1;
    int b = 2;
    int c = 3;
    int d = 4;
    int e = 5;

    List<Token> input =
        List.of(
            getLiteral(a),
            getOperator(mult),
            getOpenParen(),
            getLiteral(b),
            getOperator(sum),
            getLiteral(c),
            getCloseParen(),
            getOperator(sum),
            getLiteral(d),
            getOperator(mult),
            getLiteral(e));

    ExpressionNode exp = ParseExpression.parse(input);
    System.out.println(exp);
  }

  @Test
  public void sumFunctions() { // f1(var1, 321 + 256) + f2("arg4", var2)
    String op = "+";
    String f1 = "func1";
    String arg1 = "var1";
    int n1 = 321;
    int n2 = 256;
    String f2 = "func2";
    String arg4 = "arg4";
    String arg5 = "var2";

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
            getLiteral(arg4),
            getComma(),
            getIdentifier(arg5),
            getCloseParen());

    ExpressionNode exp = ParseExpression.parse(input);
    System.out.println(exp);
  }
}
