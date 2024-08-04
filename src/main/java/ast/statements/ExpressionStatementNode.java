package ast.statements;

import ast.interfaces.ExpressionNode;
import ast.interfaces.StatementNode;
import utils.LexicalRange;

public record ExpressionStatementNode(
    LexicalRange start,
    LexicalRange end,
    ExpressionNode expression) implements StatementNode {
}
