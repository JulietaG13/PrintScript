package ast.expressions;

import ast.interfaces.ExpressionNode;
import utils.LexicalRange;

public record BinaryExpressionNode(
    LexicalRange start,
    LexicalRange end,
    String operator,
    ExpressionNode left,
    ExpressionNode right) implements ExpressionNode {
}
