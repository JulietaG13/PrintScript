package edu;

import edu.ast.expressions.CallExpressionNode;
import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralStringNode;
import edu.context.VariableContext;
import edu.handlers.expressions.PrintExpressionHandler;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrintExpressionHandlerTest {

  @Test
  public void testPrintlnFunction() {
    LexicalRange range = new LexicalRange(0, 0, 0);
    IdentifierNode callee = new IdentifierNode(range, range, "println");
    LiteralStringNode argument = new LiteralStringNode(range, range, "Hello, World!");
    CallExpressionNode printCall = new CallExpressionNode(range, range, callee, List.of(argument));

    VariableContext variableContext =
        new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>());
    Inventory inventory = new Inventory(List.of(variableContext));
    InterpreterReader reader =
        new InterpreterReader(new java.util.Stack<>(), new java.util.Stack<>());
    PrintExpressionHandler handler = new PrintExpressionHandler(new ConsolePrinter());

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outContent));

    reader = reader.addLiteral("Hello, World!");
    reader = reader.addIdentifier("println");

    handler.handle(printCall, reader, inventory);

    System.setOut(originalOut);

    String expectedOutput = "Hello, World!";
    String actualOutput = outContent.toString().trim();
    Assertions.assertEquals(expectedOutput, actualOutput);
  }

  @Test
  public void testUnsupportedFunction() {

    LexicalRange range = new LexicalRange(0, 0, 0);
    IdentifierNode callee = new IdentifierNode(range, range, "unsupportedFunction");
    LiteralStringNode argument = new LiteralStringNode(range, range, "This should fail");
    CallExpressionNode unsupportedCall =
        new CallExpressionNode(range, range, callee, List.of(argument));

    VariableContext variableContext =
        new VariableContext(new HashMap<>(), new HashMap<>(), new HashMap<>());
    Inventory inventory = new Inventory(List.of(variableContext));
    InterpreterReader reader =
        new InterpreterReader(new java.util.Stack<>(), new java.util.Stack<>());
    PrintExpressionHandler handler = new PrintExpressionHandler(new ConsolePrinter());

    Assertions.assertThrows(
        RuntimeException.class,
        () -> handler.handle(unsupportedCall, reader, inventory),
        "Unsupported function: unsupportedFunction");
  }
}
