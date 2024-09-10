package edu;

import edu.ast.expressions.IdentifierNode;
import edu.ast.statements.Kind;
import edu.ast.statements.Type;
import edu.ast.statements.VariableDeclarationNode;
import edu.context.VariableContext;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.rules.RuleResult;
import edu.rules.declarations.VerifyNoDuplicateDeclarationInBlockRule;
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

    LexicalRange range = new LexicalRange(0, 0, 0);
    IdentifierNode idNode = new IdentifierNode(range, range, "myVar");
    varDeclNode = new VariableDeclarationNode(range, range, idNode, Type.STRING, Kind.LET, null);

    VariableContext variableContext =
        new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>());
    inventory = new Inventory(List.of(variableContext));

    reader = new InterpreterReader(new java.util.Stack<>(), new java.util.Stack<>());
    reader = reader.addIdentifier("myVar");
  }

  @Test
  public void testNoDuplicateDeclaration() {
    RuleResult result = rule.apply(varDeclNode, reader, inventory);

    Assertions.assertTrue(result.result());
    Assertions.assertEquals(reader, result.reader());
    Assertions.assertEquals(inventory, result.inventory());
  }
}
