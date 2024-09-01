package edu.common;

import static edu.ParserTestUtil.createIteratorFromString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.Lexer;
import edu.LexerFactory;
import edu.Parser;
import edu.ParserFactory;
import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralBooleanNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.IfStatementNode;
import org.junit.jupiter.api.Test;

public class IfTest {

  private static final String lineSeparator = System.lineSeparator();

  @Test
  public void emptyTrueIf() {
    String input = "if (true) { }";

    Lexer lexer = LexerFactory.createLexerV2(createIteratorFromString(input));
    Parser parser = ParserFactory.createParserV2(lexer);

    assertTrue(parser.hasNext());
    StatementNode statement = parser.next();
    assertFalse(parser.hasNext());

    assert statement instanceof IfStatementNode;
    IfStatementNode ifStatement = (IfStatementNode) statement;

    assert ifStatement.condition() instanceof LiteralBooleanNode;
    LiteralBooleanNode condition = (LiteralBooleanNode) ifStatement.condition();
    assertTrue(condition.value());

    assertTrue(ifStatement.thenDo().statements().isEmpty());
  }

  @Test
  public void emptyTrueIfElse() {
    String input = "if (true) { } " + lineSeparator + "else { }";

    Lexer lexer = LexerFactory.createLexerV2(createIteratorFromString(input));
    Parser parser = ParserFactory.createParserV2(lexer);

    assertTrue(parser.hasNext());
    StatementNode statement = parser.next();
    assertFalse(parser.hasNext());

    assert statement instanceof IfStatementNode;
    IfStatementNode ifStatement = (IfStatementNode) statement;

    assert ifStatement.condition() instanceof LiteralBooleanNode;
    LiteralBooleanNode condition = (LiteralBooleanNode) ifStatement.condition();
    assertTrue(condition.value());

    assertTrue(ifStatement.thenDo().statements().isEmpty());
  }

  @Test
  public void falseIf() {
    String input =
        "if (false) {"
            + lineSeparator
            + "let a : number = 2 + 2;"
            + lineSeparator
            + "println(2 + 2);"
            + lineSeparator
            + "println(a);"
            + lineSeparator
            + "}";

    Lexer lexer = LexerFactory.createLexerV2(createIteratorFromString(input));
    Parser parser = ParserFactory.createParserV2(lexer);

    assertTrue(parser.hasNext());
    StatementNode statement = parser.next();
    assertFalse(parser.hasNext());

    assert statement instanceof IfStatementNode;
    IfStatementNode ifStatement = (IfStatementNode) statement;

    assert ifStatement.condition() instanceof LiteralBooleanNode;
    LiteralBooleanNode condition = (LiteralBooleanNode) ifStatement.condition();
    assertFalse(condition.value());

    assertFalse(ifStatement.thenDo().statements().isEmpty());
    assertEquals(3, ifStatement.thenDo().statements().size());
  }

  @Test
  public void variableIf() {
    String input =
        "let a : boolean = true;"
            + lineSeparator
            + "if (a) {"
            + lineSeparator
            + "println(2 + 2);"
            + lineSeparator
            + "}";

    Lexer lexer = LexerFactory.createLexerV2(createIteratorFromString(input));
    Parser parser = ParserFactory.createParserV2(lexer);

    assertTrue(parser.hasNext());
    parser.next();
    assertTrue(parser.hasNext());
    StatementNode statement = parser.next();
    assertFalse(parser.hasNext());

    assert statement instanceof IfStatementNode;
    IfStatementNode ifStatement = (IfStatementNode) statement;

    assert ifStatement.condition() instanceof IdentifierNode;
    IdentifierNode condition = (IdentifierNode) ifStatement.condition();
    assertEquals(condition.name(), "a");

    assertFalse(ifStatement.thenDo().statements().isEmpty());
    assertEquals(1, ifStatement.thenDo().statements().size());
  }
}
