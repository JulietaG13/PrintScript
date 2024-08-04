package ast.expressions;

import ast.interfaces.ExpressionNode;
import utils.LexicalRange;

public record IdentifierNode(
    LexicalRange start,
    LexicalRange end,
    String name) implements ExpressionNode {
}
