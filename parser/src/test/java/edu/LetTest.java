package edu;

import static edu.TokenProvider.getColon;
import static edu.TokenProvider.getEquals;
import static edu.TokenProvider.getIdentifier;
import static edu.TokenProvider.getLet;
import static edu.TokenProvider.getLiteral;
import static edu.TokenProvider.getNumber;
import static edu.TokenProvider.getSemicolon;
import static edu.TokenProvider.getString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import edu.ast.ProgramNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.Type;
import edu.ast.statements.VariableDeclarationNode;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LetTest {

  @Test
  public void noInitialValue() { // let a : Number;
    String var = "a";
    List<Token> input =
        List.of(getLet(), getIdentifier("a"), getColon(), getNumber(), getSemicolon());

    ProgramNode root = new Parser().parse(input, true);
    List<StatementNode> statements = root.getBody();
    assertEquals(1, statements.size());

    StatementNode statement = statements.getFirst();
    assert statement instanceof VariableDeclarationNode;

    VariableDeclarationNode declaration = (VariableDeclarationNode) statement;
    assertEquals(var, declaration.id().name());

    assertEquals(Type.NUMBER, declaration.type());
    assertNull(declaration.init());
  }

  @Test
  public void initialNumberValue() {
    String var = "var";
    double value = 123.45;

    List<Token> input =
        List.of(
            getLet(),
            getIdentifier(var),
            getColon(),
            getNumber(),
            getEquals(),
            getLiteral(value),
            getSemicolon());

    ProgramNode root = new Parser().parse(input, true);
    List<StatementNode> statements = root.getBody();
    assertEquals(1, statements.size());

    StatementNode statement = statements.getFirst();
    assert statement instanceof VariableDeclarationNode;

    VariableDeclarationNode declaration = (VariableDeclarationNode) statement;
    assertEquals(var, declaration.id().name());

    assertEquals(Type.NUMBER, declaration.type());
    assertNotNull(declaration.init());

    assert declaration.init() instanceof LiteralNumberNode;
    LiteralNumberNode literal = (LiteralNumberNode) declaration.init();
    assertEquals(value, literal.value());
  }

  @Test
  public void initialStringValue() {
    String var = "string-var";
    String value = "aaabbbccc";

    List<Token> input =
        List.of(
            getLet(),
            getIdentifier(var),
            getColon(),
            getString(),
            getEquals(),
            getLiteral(value),
            getSemicolon());

    ProgramNode root = new Parser().parse(input, true);
    List<StatementNode> statements = root.getBody();
    assertEquals(1, statements.size());

    StatementNode statement = statements.getFirst();
    assert statement instanceof VariableDeclarationNode;

    VariableDeclarationNode declaration = (VariableDeclarationNode) statement;
    assertEquals(var, declaration.id().name());

    assertEquals(Type.STRING, declaration.type());
    assertNotNull(declaration.init());

    assert declaration.init() instanceof LiteralStringNode;
    LiteralStringNode literal = (LiteralStringNode) declaration.init();
    assertEquals(value, literal.value());
  }
}
