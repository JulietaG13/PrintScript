package edu;

import static edu.TokenProvider.getEquals;
import static edu.TokenProvider.getIdentifier;
import static edu.TokenProvider.getLiteral;
import static edu.TokenProvider.getOperator;
import static edu.TokenProvider.getSemicolon;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ast.ProgramNode;
import edu.ast.expressions.BinaryExpressionNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.AssignmentNode;
import edu.tokens.Token;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AssignationTest {

  @Test
  public void simpleAssign() { // var = 4
    String var = "var";
    int value = 4;
    List<Token> input = List.of(getIdentifier(var), getEquals(), getLiteral(value), getSemicolon());

    ProgramNode root = new Parser().parse(input, true);
    StatementNode statement = root.getBody().getFirst();

    assert statement instanceof AssignmentNode;
    AssignmentNode assignment = (AssignmentNode) statement;

    assertEquals(var, assignment.id().name());

    assert assignment.value() instanceof LiteralNumberNode;
    LiteralNumberNode literal = (LiteralNumberNode) assignment.value();
    assertEquals(value, literal.value());
  }

  @Test
  public void expressionAssign() { // var = "hola" + 5
    String var = "var";
    String str = "hola";
    int number = 5;
    String op = "+";
    List<Token> input =
        List.of(
            getIdentifier(var),
            getEquals(),
            getLiteral(str),
            getOperator(op),
            getLiteral(number),
            getSemicolon());

    ProgramNode root = new Parser().parse(input, true);
    StatementNode statement = root.getBody().getFirst();

    assert statement instanceof AssignmentNode;
    AssignmentNode assignment = (AssignmentNode) statement;

    assertEquals(var, assignment.id().name());

    assert assignment.value() instanceof BinaryExpressionNode;
    BinaryExpressionNode binaryExp = (BinaryExpressionNode) assignment.value();

    assert binaryExp.left() instanceof LiteralStringNode;
    assert binaryExp.right() instanceof LiteralNumberNode;
    assertEquals(op, binaryExp.operator());

    LiteralStringNode left = (LiteralStringNode) binaryExp.left();
    LiteralNumberNode right = (LiteralNumberNode) binaryExp.right();

    assertEquals(str, left.value());
    assertEquals(number, right.value());
  }
}
