package parser.ast.interfaces;

import parser.ast.expressions.LiteralType;

public interface LiteralNode extends ExpressionNode {
  LiteralType getType();
}
