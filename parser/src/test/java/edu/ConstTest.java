package edu;

import static edu.ParserTestUtil.createInputStreamFromString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ast.expressions.LiteralBooleanNode;
import edu.ast.expressions.LiteralNumberNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.Kind;
import edu.ast.statements.Type;
import edu.ast.statements.VariableDeclarationNode;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class ConstTest {

  @Test
  public void initialNumberValue() {
    String input = "const var : number = 123;";
    String var = "var";
    BigDecimal value = new BigDecimal(123);

    Lexer lexer = LexerFactory.createLexerV2(createInputStreamFromString(input));
    Parser parser = ParserFactory.createParserV2(lexer);

    assertTrue(parser.hasNext());
    StatementNode statement = parser.next();
    assertFalse(parser.hasNext());
    assert statement instanceof VariableDeclarationNode;

    VariableDeclarationNode declaration = (VariableDeclarationNode) statement;
    assertEquals(Kind.CONST, declaration.kind());
    assertEquals(var, declaration.id().name());

    assertEquals(Type.NUMBER, declaration.type());
    assertNotNull(declaration.init());

    assert declaration.init() instanceof LiteralNumberNode;
    LiteralNumberNode literal = (LiteralNumberNode) declaration.init();
    assertEquals(value, literal.value());
  }

  @Test
  public void initialStringValue() {
    String input = "const var : string = \"aaabbbccc\";";
    String var = "var";
    String value = "aaabbbccc";

    Lexer lexer = LexerFactory.createLexerV2(createInputStreamFromString(input));
    Parser parser = ParserFactory.createParserV2(lexer);

    assertTrue(parser.hasNext());
    StatementNode statement = parser.next();
    assertFalse(parser.hasNext());
    assert statement instanceof VariableDeclarationNode;

    VariableDeclarationNode declaration = (VariableDeclarationNode) statement;
    assertEquals(Kind.CONST, declaration.kind());
    assertEquals(var, declaration.id().name());

    assertEquals(Type.STRING, declaration.type());
    assertNotNull(declaration.init());

    assert declaration.init() instanceof LiteralStringNode;
    LiteralStringNode literal = (LiteralStringNode) declaration.init();
    assertEquals(value, literal.value());
  }

  @Test
  public void initialBooleanValue() {
    String input = "const var : boolean = true;";
    String var = "var";
    boolean value = true;

    Lexer lexer = LexerFactory.createLexerV2(createInputStreamFromString(input));
    Parser parser = ParserFactory.createParserV2(lexer);

    assertTrue(parser.hasNext());
    StatementNode statement = parser.next();
    assertFalse(parser.hasNext());
    assert statement instanceof VariableDeclarationNode;

    VariableDeclarationNode declaration = (VariableDeclarationNode) statement;
    assertEquals(Kind.CONST, declaration.kind());
    assertEquals(var, declaration.id().name());

    assertEquals(Type.BOOLEAN, declaration.type());
    assertNotNull(declaration.init());

    assert declaration.init() instanceof LiteralBooleanNode;
    LiteralBooleanNode literal = (LiteralBooleanNode) declaration.init();
    assertEquals(value, literal.value());
  }
}
