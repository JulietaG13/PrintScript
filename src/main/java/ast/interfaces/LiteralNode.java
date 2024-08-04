package ast.interfaces;

import ast.expressions.LiteralType;

public interface LiteralNode extends ExpressionNode {
  LiteralType getType();
}
