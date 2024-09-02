package edu.check;

import edu.ast.AstVisitor;
import edu.ast.BlockNode;
import edu.ast.ProgramNode;
import edu.ast.expressions.BinaryExpressionNode;
import edu.ast.expressions.CallExpressionNode;
import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralBooleanNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.AssignmentNode;
import edu.ast.statements.ExpressionStatementNode;
import edu.ast.statements.IfStatementNode;
import edu.ast.statements.Type;
import edu.ast.statements.VariableDeclarationNode;
import java.util.HashMap;
import java.util.Map;

public class ParserVisitor implements AstVisitor {

  private Map<String, Type> vars = new HashMap<>();
  private Type lastType = null;

  public ParserVisitor(ProgramNode root) {
    root.accept(this);
  }

  @Override
  public void visit(ProgramNode node) {
    for (StatementNode statement : node.getBody()) {
      statement.accept(this);
    }
    lastType = null;
  }

  @Override
  public void visit(AssignmentNode node) {
    // dont visit id
    String name = node.id().name();
    if (!vars.containsKey(name)) {
      throw new VariableNotDeclaredException(name);
    }

    node.value().accept(this);

    Type expected = vars.get(name);
    if (expected != lastType) {
      throw new VariableTypeMismatchException(name, expected, lastType);
    }

    lastType = null;
  }

  @Override
  public void visit(ExpressionStatementNode node) {
    node.expression().accept(this);
    lastType = null;
  }

  @Override
  public void visit(VariableDeclarationNode node) {
    // dont visit id
    String name = node.id().name();
    vars.put(name, node.type());

    if (node.init() == null) {
      return;
    }

    node.init().accept(this);
    if (node.type() != lastType) {
      throw new VariableTypeMismatchException(name, node.type(), lastType);
    }

    lastType = null;
  }

  @Override
  public void visit(BinaryExpressionNode node) {
    node.left().accept(this);
    Type left = lastType;

    node.right().accept(this);
    Type right = lastType;

    lastType = Type.combine(left, right);
  }

  @Override
  public void visit(CallExpressionNode node) {
    // dont visit the callee
    for (ExpressionNode exp : node.args()) {
      exp.accept(this);
    }
    lastType = null;
  }

  @Override
  public void visit(IdentifierNode node) {
    // only supposed to be called inside expressions
    if (!vars.containsKey(node.name())) {
      throw new VariableNotDeclaredException(node.name());
    }
    lastType = vars.get(node.name());
  }

  @Override
  public void visit(LiteralNumberNode node) {
    lastType = Type.NUMBER;
  }

  @Override
  public void visit(LiteralStringNode node) {
    lastType = Type.STRING;
  }

  @Override
  public void visit(IfStatementNode node) {}

  @Override
  public void visit(BlockNode node) {}

  @Override
  public void visit(LiteralBooleanNode node) {}
}
