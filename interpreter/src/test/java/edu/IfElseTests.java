package edu;

import static edu.InterpreterFactory.createInterpreterV2;
import static edu.LexerFactory.createLexerV2;
import static edu.ParserFactory.createParserV2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ast.BlockNode;
import edu.ast.ProgramNode;
import edu.ast.expressions.CallExpressionNode;
import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralBooleanNode;
import edu.ast.expressions.LiteralStringNode;
import edu.ast.interfaces.ExpressionNode;
import edu.ast.interfaces.StatementNode;
import edu.ast.statements.ExpressionStatementNode;
import edu.ast.statements.IfStatementNode;
import edu.context.VariableContext;
import edu.handlers.HandlerRegistryV2;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.rules.RuleProviderV2;
import edu.visitor.ExecutionVisitor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class IfElseTests {

  private InputStream createInputStreamFromString(String code) {
    return new ByteArrayInputStream(code.getBytes());
  }

  private Interpreter createInterpreter(String code) {
    InputStream codeIterator = createInputStreamFromString(code);
    Lexer lexer = createLexerV2(codeIterator);
    Parser parser = createParserV2(lexer);
    return createInterpreterV2(parser);
  }

  public String getPrintedInfo(ProgramNode node) {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outContent));
    try {
      interpret(node);
    } finally {
      System.setOut(originalOut);
    }
    String actualOutput = outContent.toString().trim();
    return actualOutput;
  }

  public VariableContext interpret(ProgramNode node) {
    VariableContext variableContext =
        new VariableContext(
            new java.util.HashMap<>(), new java.util.HashMap<>(), new java.util.HashMap<>());
    InterpreterReader reader =
        new InterpreterReader(new java.util.Stack<>(), new java.util.Stack<>());
    List context = new ArrayList();
    context.add(variableContext);
    Inventory inventory = new Inventory(context);
    ExecutionVisitor visitor =
        new ExecutionVisitor(reader, inventory, new HandlerRegistryV2(new RuleProviderV2()));
    visitor.visit(node);
    return visitor.getInventory().getVariableContext();
  }

  @Test
  public void testIfParser() {
    String input =
        "if (false) {"
            + System.lineSeparator()
            + "let a : number = 2 + 2;"
            + System.lineSeparator()
            + "println(2 + 2);"
            + System.lineSeparator()
            + "println(a);"
            + System.lineSeparator()
            + "}";

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    Interpreter interpreter = createInterpreter(input);
    interpreter.interpret();

    System.setOut(originalOut);

    // Get the captured output
    String output = outputStream.toString().trim();

    // Check that there is no output since the if condition is false
    assertEquals("", output, "Expected no " + "output when the if condition is false.");
  }

  @Test
  public void variableIf() {
    String input =
        "let a : boolean = true;"
            + System.lineSeparator()
            + "if (a) {"
            + System.lineSeparator()
            + "println(2 + 2);"
            + System.lineSeparator()
            + "}";

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    Interpreter interpreter = createInterpreter(input);

    interpreter.interpret();
    System.setOut(originalOut);

    String output = outputStream.toString().trim();

    assertEquals("4", output, "Expected output to be 4.");
  }

  @Test
  public void falseIf() {
    String input =
        "if (false) {"
            + System.lineSeparator()
            + "let a : number = 2 + 2;"
            + System.lineSeparator()
            + "println(2 + 2);"
            + System.lineSeparator()
            + "println(a);"
            + System.lineSeparator()
            + "}";

    Lexer lexer = createLexerV2(createInputStreamFromString(input));
    Parser parser = createParserV2(lexer);

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
  public void testIfTrueThenBlockExecuted() {
    LexicalRange range = new LexicalRange(0, 0, 0);

    ExpressionNode condition = new LiteralBooleanNode(range, range, true);

    List<StatementNode> thenStatements =
        Collections.singletonList(
            new ExpressionStatementNode(
                range,
                range,
                new CallExpressionNode(
                    range,
                    range,
                    new IdentifierNode(range, range, "println"),
                    Collections.singletonList(
                        new LiteralStringNode(range, range, "thenDo executed")))));
    BlockNode thenBlock = new BlockNode(range, range, thenStatements);

    BlockNode elseBlock = null; // No hay bloque else

    IfStatementNode ifStatement =
        new IfStatementNode(range, range, condition, thenBlock, elseBlock);

    ProgramNode program = new ProgramNode();
    program.addStatement(ifStatement);

    String output = getPrintedInfo(program);

    assertEquals("thenDo executed", output);
  }

  @Test
  public void testIfFalseElseBlockExecuted() {
    LexicalRange range = new LexicalRange(0, 0, 0);

    ExpressionNode condition = new LiteralBooleanNode(range, range, false);

    List<StatementNode> thenStatements =
        Collections.singletonList(
            new ExpressionStatementNode(
                range,
                range,
                new CallExpressionNode(
                    range,
                    range,
                    new IdentifierNode(range, range, "println"),
                    Collections.singletonList(
                        new LiteralStringNode(range, range, "thenDo executed")))));
    BlockNode thenBlock = new BlockNode(range, range, thenStatements);

    List<StatementNode> elseStatements =
        Collections.singletonList(
            new ExpressionStatementNode(
                range,
                range,
                new CallExpressionNode(
                    range,
                    range,
                    new IdentifierNode(range, range, "println"),
                    Collections.singletonList(
                        new LiteralStringNode(range, range, "elseDo executed")))));
    BlockNode elseBlock = new BlockNode(range, range, elseStatements);

    IfStatementNode ifStatement =
        new IfStatementNode(range, range, condition, thenBlock, elseBlock);

    ProgramNode program = new ProgramNode();
    program.addStatement(ifStatement);

    String output = getPrintedInfo(program);

    assertEquals("elseDo executed", output);
  }

  @Test
  public void testIfTrueNoElseBlockExecuted() {
    LexicalRange range = new LexicalRange(0, 0, 0);

    ExpressionNode condition = new LiteralBooleanNode(range, range, true);

    List<StatementNode> thenStatements =
        Collections.singletonList(
            new ExpressionStatementNode(
                range,
                range,
                new CallExpressionNode(
                    range,
                    range,
                    new IdentifierNode(range, range, "println"),
                    Collections.singletonList(
                        new LiteralStringNode(range, range, "thenDo executed")))));
    BlockNode thenBlock = new BlockNode(range, range, thenStatements);

    IfStatementNode ifStatement = new IfStatementNode(range, range, condition, thenBlock, null);

    ProgramNode program = new ProgramNode();
    program.addStatement(ifStatement);

    String output = getPrintedInfo(program);

    assertEquals("thenDo executed", output);
  }

  @Test
  public void testIfFalseThenElseBlockExecuted() {
    LexicalRange range = new LexicalRange(0, 0, 0);

    ExpressionNode condition = new LiteralBooleanNode(range, range, false);

    List<StatementNode> thenStatements =
        Collections.singletonList(
            new ExpressionStatementNode(
                range,
                range,
                new CallExpressionNode(
                    range,
                    range,
                    new IdentifierNode(range, range, "println"),
                    Collections.singletonList(
                        new LiteralStringNode(range, range, "thenDo executed")))));
    BlockNode thenBlock = new BlockNode(range, range, thenStatements);

    List<StatementNode> elseStatements =
        Collections.singletonList(
            new ExpressionStatementNode(
                range,
                range,
                new CallExpressionNode(
                    range,
                    range,
                    new IdentifierNode(range, range, "println"),
                    Collections.singletonList(
                        new LiteralStringNode(range, range, "elseDo executed")))));
    BlockNode elseBlock = new BlockNode(range, range, elseStatements);

    IfStatementNode ifStatement =
        new IfStatementNode(range, range, condition, thenBlock, elseBlock);

    ProgramNode program = new ProgramNode();
    program.addStatement(ifStatement);

    String output = getPrintedInfo(program);

    assertEquals("elseDo executed", output);
  }
}
