package edu;

import edu.ast.ProgramNode;
import edu.ast.expressions.*;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.statements.ExpressionStatementNode;
import edu.ast.statements.Kind;
import edu.ast.statements.Type;
import edu.ast.statements.VariableDeclarationNode;
import edu.utils.LexicalRange;
import java.util.ArrayList;
import java.util.List;

public class InterpreterTest {
  public static void main(String[] args) {
    IdentifierNode varName =
        new IdentifierNode(
            new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "my_cool_variable");
    LiteralStringNode varValue =
        new LiteralStringNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "ciclon");
    VariableDeclarationNode declarationNode =
        new VariableDeclarationNode(
            new LexicalRange(0, 0, 0),
            new LexicalRange(0, 0, 0),
            varName,
            Type.STRING,
            Kind.LET,
            varValue);

    List<ExpressionNode> argsList = new ArrayList<>();
    argsList.add(varName);
    CallExpressionNode callExpression =
        new CallExpressionNode(
            new LexicalRange(0, 0, 0),
            new LexicalRange(0, 0, 0),
            new IdentifierNode(new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), "println"),
            argsList);

    ExpressionStatementNode printNode =
        new ExpressionStatementNode(
            new LexicalRange(0, 0, 0), new LexicalRange(0, 0, 0), callExpression);

    ProgramNode programNode = new ProgramNode();
    programNode.addStatement(declarationNode);
    programNode.addStatement(printNode);

    Interpreter interpreter = new Interpreter();
    interpreter.interpret(programNode);
  }
}
