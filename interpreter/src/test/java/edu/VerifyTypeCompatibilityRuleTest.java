package edu;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.statements.AssignmentNode;
import edu.context.VariableContext;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.rules.assignments.VerifyTypeCompatibilityRule;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class VerifyTypeCompatibilityRuleTest {

  @Test
  public void testCompatibleAssignment() {
    LexicalRange range = new LexicalRange(0, 0, 0);
    IdentifierNode id = new IdentifierNode(range, range, "myVar");
    LiteralNumberNode value = new LiteralNumberNode(range, range, new BigDecimal(42.0));
    AssignmentNode assignmentNode = new AssignmentNode(range, range, "=", id, value);

    VariableContext variableContext =
        new VariableContext(
            new HashMap<>(Map.of("myVar", new BigDecimal(10))), new HashMap<>(), new HashMap<>());
    Inventory inventory = new Inventory(List.of(variableContext));
    InterpreterReader reader =
        new InterpreterReader(new java.util.Stack<>(), new java.util.Stack<>());
    reader = reader.addIdentifier("myVar");
    reader = reader.addLiteral(42.0);
    VerifyTypeCompatibilityRule rule = new VerifyTypeCompatibilityRule();

    boolean result = rule.apply(assignmentNode, reader, inventory);

    assertTrue(result);
  }

  @Test
  public void testIncompatibleAssignmentThrowsException() {
    LexicalRange range = new LexicalRange(0, 0, 0);
    IdentifierNode id = new IdentifierNode(range, range, "myVar");
    LiteralStringNode value = new LiteralStringNode(range, range, "incompatible");
    AssignmentNode assignmentNode = new AssignmentNode(range, range, "=", id, value);

    VariableContext variableContext =
        new VariableContext(
            new HashMap<>(Map.of("myVar", new BigDecimal(10))), new HashMap<>(), new HashMap<>());
    Inventory inventory = new Inventory(List.of(variableContext));
    InterpreterReader reader =
        new InterpreterReader(new java.util.Stack<>(), new java.util.Stack<>());
    VerifyTypeCompatibilityRule rule = new VerifyTypeCompatibilityRule();
    reader = reader.addIdentifier("myVar");
    reader = reader.addLiteral("incompatible");
    InterpreterReader finalReader = reader;
    assertThrows(
        RuntimeException.class,
        () -> rule.apply(assignmentNode, finalReader, inventory),
        "Invalid assignment: Type mismatch or undefined variable: myVar");
  }
}
