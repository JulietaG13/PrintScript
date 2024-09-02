package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.statements.Kind;
import edu.ast.statements.Type;
import edu.ast.statements.VariableDeclarationNode;
import edu.context.ConstantContext;
import edu.context.VariableContext;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.rules.RuleResult;
import edu.rules.declarations.VerifyConstantNotRedefinedRule;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;

public class VerifyConstantNotRedefinedRuleTest {

  @Test
  public void testRedefiningConstantThrowsException() {
    // Setup
    LexicalRange range = new LexicalRange(0, 0, 0);
    IdentifierNode id = new IdentifierNode(range, range, "myConst");
    VariableDeclarationNode varDeclNode =
        new VariableDeclarationNode(
            range, range, id, Type.STRING, Kind.CONST, new LiteralStringNode(range, range, "test"));

    // Constant "myConst" is already defined
    ConstantContext constantContext = new ConstantContext(new HashSet<>(List.of("myConst")));
    Inventory inventory =
        new Inventory(
            List.of(
                new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>()),
                constantContext));
    InterpreterReader reader =
        new InterpreterReader(new java.util.Stack<>(), new java.util.Stack<>());

    VerifyConstantNotRedefinedRule rule = new VerifyConstantNotRedefinedRule();

    // Act & Assert
    assertThrows(
        RuntimeException.class,
        () -> rule.apply(varDeclNode, reader, inventory),
        "Constant 'myConst' is already defined in the current context.");
  }

  @Test
  public void testNonRedefiningConstantSucceeds() {
    // Setup
    LexicalRange range = new LexicalRange(0, 0, 0);
    IdentifierNode id = new IdentifierNode(range, range, "newConst");
    VariableDeclarationNode varDeclNode =
        new VariableDeclarationNode(range, range, id, Type.STRING, Kind.CONST, null);

    // No constants are defined yet
    ConstantContext constantContext = new ConstantContext(new HashSet<>());
    Inventory inventory =
        new Inventory(
            List.of(
                new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>()),
                constantContext));
    InterpreterReader reader =
        new InterpreterReader(new java.util.Stack<>(), new java.util.Stack<>());

    VerifyConstantNotRedefinedRule rule = new VerifyConstantNotRedefinedRule();
    reader = reader.addIdentifier("newConst");

    // Act
    RuleResult result = rule.apply(varDeclNode, reader, inventory);

    // Assert
    assertTrue(result.result());
    assertEquals(reader, result.reader());
    assertEquals(inventory, result.inventory());
  }
}
