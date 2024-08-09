package edu.ast.interfaces;

import edu.ast.expressions.LiteralType;

public interface LiteralNode extends ExpressionNode {
  LiteralType getType();
}
