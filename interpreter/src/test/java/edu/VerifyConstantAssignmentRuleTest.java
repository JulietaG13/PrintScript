package edu;

import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.statements.AssignmentNode;
import edu.context.ConstantContext;
import edu.context.VariableContext;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.rules.assignments.VerifyConstantAssignmentRule;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerifyConstantAssignmentRuleTest {

  @Test
  public void testAssignmentToConstantThrowsException() {
    LexicalRange range = new LexicalRange(0, 0, 0);
    IdentifierNode id = new IdentifierNode(range, range, "myConst");
    LiteralNumberNode value = new LiteralNumberNode(range, range, new BigDecimal(42.0));
    AssignmentNode assignmentNode = new AssignmentNode(range, range, "=", id, value);

    ConstantContext constantContext = new ConstantContext(new HashSet<>(List.of("myConst")));
    Inventory inventory =
        new Inventory(
            List.of(
                new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>()),
                constantContext));
    InterpreterReader reader =
        new InterpreterReader(new java.util.Stack<>(), new java.util.Stack<>());

    VerifyConstantAssignmentRule rule = new VerifyConstantAssignmentRule();

    Assertions.assertThrows(
        RuntimeException.class,
        () -> rule.apply(assignmentNode, reader, inventory),
        "Cannot assign to a constant variable: myConst");
  }

  @Test
  public void testAssignmentToNonConstantSucceeds() {
    LexicalRange range = new LexicalRange(0, 0, 0);
    IdentifierNode id = new IdentifierNode(range, range, "myVar");
    LiteralNumberNode value = new LiteralNumberNode(range, range, new BigDecimal(42.0));
    AssignmentNode assignmentNode = new AssignmentNode(range, range, "=", id, value);

    ConstantContext constantContext = new ConstantContext(new HashSet<>());
    Inventory inventory =
        new Inventory(
            List.of(
                new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>()),
                constantContext));
    VariableContext variableContext =
        inventory.getVariableContext().setNumberVariable("myVar", new BigDecimal(0));
    inventory = inventory.setVariableContext(variableContext);
    InterpreterReader reader =
        new InterpreterReader(new java.util.Stack<>(), new java.util.Stack<>());

    VerifyConstantAssignmentRule rule = new VerifyConstantAssignmentRule();
    reader = reader.addIdentifier("myVar");
    boolean result = rule.apply(assignmentNode, reader, inventory);

    Assertions.assertTrue(result);
  }
}
