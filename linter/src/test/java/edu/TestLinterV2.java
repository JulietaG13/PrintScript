package edu;

import static edu.LinterFactory.createLinterV2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import edu.ast.ProgramNode;
import edu.ast.expressions.BinaryExpressionNode;
import edu.ast.expressions.CallExpressionNode;
import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.statements.ExpressionStatementNode;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestLinterV2 {

  private static final JsonObject nonExpression;

  static {
    nonExpression = new JsonObject();
    nonExpression.addProperty("lower_camel_case", true);
    nonExpression.addProperty("read_input_non_expressions", true);
  }

  @Test
  public void testReadInputNonExpression() {
    ProgramNode node = createProgramNodeCorrectFunction();
    Linter linter = createLinterV2(nonExpression);
    Report report = linter.analyze(node);
    assertTrue(report.getReport().isPresent());
    assertEquals(1, report.getReport().get().size());
    assertEquals(
        "Error in println function: "
            + "The readInput function only accepts identifiers or literals as arguments.\n"
            + "Argument 1 is invalid:\n"
            + " - Type: BinaryExpressionNode\n"
            + " - Position: LexicalRange(offset=1, line=1, column=1)\n"
            + " - Content: \"Enter a \" + \"number: \"",
        report.getReport().get().get(0));
  }

  private ProgramNode createProgramNodeCorrectFunction() {
    ProgramNode programNode = new ProgramNode();
    LexicalRange x = new LexicalRange(1, 1, 1);
    LiteralStringNode a = new LiteralStringNode(x, x, "Enter a ");
    LiteralStringNode b = new LiteralStringNode(x, x, "number: ");
    BinaryExpressionNode args = new BinaryExpressionNode(x, x, "+", a, b);
    IdentifierNode identifier = new IdentifierNode(x, x, "readInput");
    ExpressionStatementNode expressionStatementNode =
        new ExpressionStatementNode(x, x, new CallExpressionNode(x, x, identifier, List.of(args)));
    programNode.addStatement(expressionStatementNode);
    return programNode;
  }
}
