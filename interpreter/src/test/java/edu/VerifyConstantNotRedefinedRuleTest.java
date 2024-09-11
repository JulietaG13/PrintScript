package edu;

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
import edu.rules.declarations.VerifyConstantNotRedefinedRule;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;

public class VerifyConstantNotRedefinedRuleTest {

  @Test
  public void testRedefiningConstantThrowsException() {
    LexicalRange range = new LexicalRange(0, 0, 0);
    IdentifierNode id = new IdentifierNode(range, range, "myConst");
    VariableDeclarationNode varDeclNode =
        new VariableDeclarationNode(
            range, range, id, Type.STRING, Kind.CONST, new LiteralStringNode(range, range, "test"));

    ConstantContext constantContext = new ConstantContext(new HashSet<>(List.of("myConst")));
    Inventory inventory =
        new Inventory(
            List.of(
                new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>()),
                constantContext));
    InterpreterReader reader =
        new InterpreterReader(new java.util.Stack<>(), new java.util.Stack<>());

    VerifyConstantNotRedefinedRule rule = new VerifyConstantNotRedefinedRule();

    assertThrows(
        RuntimeException.class,
        () -> rule.apply(varDeclNode, reader, inventory),
        "Constant 'myConst' is already defined in the current context.");
  }

  @Test
  public void testNonRedefiningConstantSucceeds() {
    LexicalRange range = new LexicalRange(0, 0, 0);
    IdentifierNode id = new IdentifierNode(range, range, "newConst");
    VariableDeclarationNode varDeclNode =
        new VariableDeclarationNode(range, range, id, Type.STRING, Kind.CONST, null);

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

    boolean result = rule.apply(varDeclNode, reader, inventory);

    assertTrue(result);
  }
}
