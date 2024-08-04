package ast.expressions;

import ast.interfaces.ExpressionNode;
import utils.LexicalRange;

import java.util.List;

public record CallExpressionNode(
    LexicalRange start,
    LexicalRange end,
    IdentifierNode callee,
    List<ExpressionNode> args) implements ExpressionNode {
}
