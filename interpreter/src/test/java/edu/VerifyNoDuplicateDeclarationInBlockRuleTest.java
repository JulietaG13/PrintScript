package edu;

import edu.ast.expressions.IdentifierNode;
import edu.ast.statements.Kind;
import edu.ast.statements.Type;
import edu.ast.statements.VariableDeclarationNode;
import edu.context.TemporalContext;
import edu.context.VariableContext;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.rules.RuleResult;
import edu.rules.declarations.VerifyNoDuplicateDeclarationInBlockRule;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VerifyNoDuplicateDeclarationInBlockRuleTest {

  private VerifyNoDuplicateDeclarationInBlockRule rule;
  private InterpreterReader reader;
  private Inventory inventory;
  private VariableDeclarationNode varDeclNode;

  @BeforeEach
  public void setUp() {
    rule = new VerifyNoDuplicateDeclarationInBlockRule();

    // Setup an IdentifierNode and VariableDeclarationNode
    LexicalRange range = new LexicalRange(0, 0, 0);
    IdentifierNode idNode = new IdentifierNode(range, range, "myVar");
    varDeclNode = new VariableDeclarationNode(range, range, idNode, Type.STRING, Kind.LET, null);

    // Setup basic inventory with empty contexts
    TemporalContext temporalContext = new TemporalContext();
    VariableContext variableContext =
        new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>());
    inventory = new Inventory(List.of(variableContext, temporalContext));

    // Setup the InterpreterReader
    reader = new InterpreterReader(new java.util.Stack<>(), new java.util.Stack<>());
    reader = reader.addIdentifier("myVar");
  }

  @Test
  public void testNoDuplicateDeclaration() {
    // Act
    RuleResult result = rule.apply(varDeclNode, reader, inventory);

    // Assert
    Assertions.assertTrue(result.result());
    Assertions.assertEquals(reader, result.reader());
    Assertions.assertEquals(inventory, result.inventory());
  }

  @Test
  public void testDuplicateDeclarationInBlockThrowsException() {
    // Setup a temporal context with "myVar" already defined
    TemporalContext temporalContext = new TemporalContext().storeValue("myVar", 42);
    inventory = new Inventory(List.of(inventory.getVariableContext(), temporalContext));

    // Act & Assert
    RuntimeException exception =
        Assertions.assertThrows(
            RuntimeException.class,
            () -> {
              rule.apply(varDeclNode, reader, inventory);
            });
    Assertions.assertEquals(
        "Variable or constant 'myVar' is already defined in this block or context.",
        exception.getMessage());
  }

  @Test
  public void testDuplicateDeclarationInContextThrowsException() {
    // Setup a variable context with "myVar" already defined
    VariableContext variableContext =
        inventory.getVariableContext().setNumberVariable("myVar", new BigDecimal(42));
    inventory = new Inventory(List.of(variableContext, inventory.getTemporaryContext()));

    // Act & Assert
    RuntimeException exception =
        Assertions.assertThrows(
            RuntimeException.class,
            () -> {
              rule.apply(varDeclNode, reader, inventory);
            });
    Assertions.assertEquals(
        "Variable or constant 'myVar' is already defined in this block or context.",
        exception.getMessage());
  }
}
