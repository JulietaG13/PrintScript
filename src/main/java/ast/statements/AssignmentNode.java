package ast.statements;

import ast.interfaces.ExpressionNode;
import ast.expressions.IdentifierNode;
import ast.interfaces.StatementNode;
import utils.LexicalRange;

public record AssignmentNode(
    LexicalRange start,
    LexicalRange end,
    String operator,
    IdentifierNode id,
    ExpressionNode value) implements StatementNode {
}
