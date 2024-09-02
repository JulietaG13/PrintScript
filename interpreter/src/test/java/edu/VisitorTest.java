package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ast.BlockNode;
import edu.ast.ProgramNode;
import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralBooleanNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.statements.AssignmentNode;
import edu.ast.statements.IfStatementNode;
import edu.ast.statements.Kind;
import edu.ast.statements.Type;
import edu.ast.statements.VariableDeclarationNode;
import edu.context.ConstantContext;
import edu.context.VariableContext;
import edu.handlers.HandlerRegistryV2;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.rules.RuleProviderV2;
import edu.visitor.ExecutionVisitor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.Test;

public class VisitorTest {

  private ExecutionVisitor createVisitor() {
    VariableContext variableContext =
        new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>());
    Inventory inventory =
        new Inventory(List.of(variableContext, new ConstantContext(new HashSet<>())));
    InterpreterReader reader =
        new InterpreterReader(new java.util.Stack<>(), new java.util.Stack<>());
    return new ExecutionVisitor(reader, inventory, new HandlerRegistryV2(new RuleProviderV2()));
  }

  @Test
  public void testVariableDeclaration() {
    ExecutionVisitor visitor = createVisitor();
    ProgramNode program = new ProgramNode();

    // Create variable declaration: let x = 5;
    LexicalRange range = new LexicalRange(0, 0, 0);
    IdentifierNode id = new IdentifierNode(range, range, "x");
    LiteralNumberNode init = new LiteralNumberNode(range, range, 5.0);
    VariableDeclarationNode varDecl =
        new VariableDeclarationNode(range, range, id, Type.NUMBER, Kind.LET, init);
    program.addStatement(varDecl);

    visitor.visit(program);

    VariableContext variableContext = visitor.getInventory().getVariableContext();
    assertTrue(variableContext.hasNumberVariable("x"));
    assertEquals(5.0, variableContext.getNumberVariable("x"));
  }

  @Test
  public void testAssignment() {
    ExecutionVisitor visitor = createVisitor();
    ProgramNode program = new ProgramNode();

    // Declare and assign: let x = 5;
    LexicalRange range = new LexicalRange(0, 0, 0);
    IdentifierNode id = new IdentifierNode(range, range, "x");
    LiteralNumberNode init = new LiteralNumberNode(range, range, 5.0);
    VariableDeclarationNode varDecl =
        new VariableDeclarationNode(range, range, id, Type.NUMBER, Kind.LET, init);
    program.addStatement(varDecl);

    // Update x = 10;
    AssignmentNode assignment =
        new AssignmentNode(range, range, "=", id, new LiteralNumberNode(range, range, 10.0));
    program.addStatement(assignment);

    visitor.visit(program);

    VariableContext variableContext = visitor.getInventory().getVariableContext();
    assertTrue(variableContext.hasNumberVariable("x"));
    assertEquals(10.0, variableContext.getNumberVariable("x"));
  }

  @Test
  public void testIfStatementTrueBranch() {
    ExecutionVisitor visitor = createVisitor();
    ProgramNode program = new ProgramNode();

    // Declare let condition = true;
    LexicalRange range = new LexicalRange(0, 0, 0);
    IdentifierNode idCondition = new IdentifierNode(range, range, "condition");
    LiteralBooleanNode initCondition = new LiteralBooleanNode(range, range, true);
    VariableDeclarationNode varDeclCondition =
        new VariableDeclarationNode(
            range, range, idCondition, Type.BOOLEAN, Kind.LET, initCondition);
    program.addStatement(varDeclCondition);

    // If condition is true, execute then branch (let x = 5;)
    IdentifierNode id = new IdentifierNode(range, range, "x");
    LiteralNumberNode init = new LiteralNumberNode(range, range, 5.0);
    VariableDeclarationNode thenDecl =
        new VariableDeclarationNode(range, range, id, Type.NUMBER, Kind.LET, init);
    BlockNode thenBlock = new BlockNode(range, range, List.of(thenDecl));
    BlockNode elseBlock = null; // No else block
    IfStatementNode ifStatement =
        new IfStatementNode(range, range, idCondition, thenBlock, elseBlock);
    program.addStatement(ifStatement);

    visitor.visit(program);

    VariableContext variableContext = visitor.getInventory().getVariableContext();
    assertTrue(!variableContext.hasNumberVariable("x"));
    assertEquals(null, visitor.getInventory().getTemporaryContext());
  }
}
