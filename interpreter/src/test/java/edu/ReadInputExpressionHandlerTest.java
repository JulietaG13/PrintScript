package edu;

import static edu.InterpreterFactory.createInterpreterV2;
import static edu.LexerFactory.createLexerV1;
import static edu.ParserFactory.createParserV1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ast.expressions.CallExpressionNode;
import edu.ast.expressions.IdentifierNode;
import edu.ast.expressions.LiteralStringNode;
import edu.handlers.expressions.ReadInputExpressionHandler;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.utils.HandlerResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReadInputExpressionHandlerTest {

  private ReadInputExpressionHandler handler;
  private Inventory inventory;
  private InterpreterReader reader;

  @BeforeEach
  void setUp() {
    handler = new ReadInputExpressionHandler();
    inventory = new Inventory(Collections.emptyList());
    reader = new InterpreterReader(new Stack<>(), new Stack<>());
  }

  @Test
  void handle_shouldReturnNumber_whenInputIsNumeric() {
    // Simulate user input "42"
    String simulatedInput = "42\n";
    System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

    // Mock CallExpressionNode for readInput
    IdentifierNode callee = new IdentifierNode(null, null, "readInput");
    LiteralStringNode argument = new LiteralStringNode(null, null, "Enter a number: ");
    CallExpressionNode callNode = new CallExpressionNode(null, null, callee, List.of(argument));

    reader = reader.addLiteral("Enter a number: ");

    HandlerResult result = handler.handle(callNode, reader, inventory);

    assertEquals(42.0, result.getInterpreterReader().getLiteral().getValue());
  }

  @Test
  void handle_shouldReturnBoolean_whenInputIsBoolean() {
    // Simulate user input "true"
    ByteArrayInputStream inContent = new ByteArrayInputStream("true\n".getBytes());
    System.setIn(inContent);

    // Mock CallExpressionNode for readInput
    IdentifierNode callee = new IdentifierNode(null, null, "readInput");
    LiteralStringNode argument = new LiteralStringNode(null, null, "Enter a boolean: ");
    CallExpressionNode callNode = new CallExpressionNode(null, null, callee, List.of(argument));

    reader = reader.addLiteral("Enter a boolean: ");

    HandlerResult result = handler.handle(callNode, reader, inventory);

    assertEquals(true, result.getInterpreterReader().getLiteral().getValue());
  }

  @Test
  void handle_shouldReturnString_whenInputIsText() {
    // Simulate user input "hello"
    ByteArrayInputStream inContent = new ByteArrayInputStream("hello\n".getBytes());
    System.setIn(inContent);

    // Mock CallExpressionNode for readInput
    IdentifierNode callee = new IdentifierNode(null, null, "readInput");
    LiteralStringNode argument = new LiteralStringNode(null, null, "Enter text: ");
    CallExpressionNode callNode = new CallExpressionNode(null, null, callee, List.of(argument));

    reader = reader.addLiteral("Enter text: ");

    HandlerResult result = handler.handle(callNode, reader, inventory);

    assertEquals("hello", result.getInterpreterReader().getLiteral().getValue());
  }

  @Test
  void handle_shouldThrowException_whenFunctionIsNotReadInput() {
    // Mock CallExpressionNode for unsupported function
    IdentifierNode callee = new IdentifierNode(null, null, "unsupportedFunction");
    LiteralStringNode argument = new LiteralStringNode(null, null, "This should fail: ");
    CallExpressionNode callNode = new CallExpressionNode(null, null, callee, List.of(argument));

    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              handler.handle(callNode, reader, inventory);
            });

    assertEquals("Unsupported function call: unsupportedFunction", exception.getMessage());
  }

  private InputStream createInputStreamFromString(String code) {
    return new ByteArrayInputStream(code.getBytes());
  }

  private Interpreter createInterpreterV(String code) {
    InputStream codeIterator = createInputStreamFromString(code);
    Lexer lexer = createLexerV1(codeIterator);
    Parser parser = createParserV1(lexer);
    return createInterpreterV2(parser);
  }

  @Test
  public void testReadInputNonExpression() {
    // Simulate user input "42"
    String simulatedInput = "42\n";
    ByteArrayInputStream inContent = new ByteArrayInputStream(simulatedInput.getBytes());
    System.setIn(inContent);

    // Define the code to test readInput
    String code = "readInput(\"Enter a \" + \"number: \");";

    Interpreter interpreter = createInterpreterV(code);

    // Redirect output to capture the result
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    // Run the interpreter
    interpreter.interpret();

    // Restore original System.out
    System.setOut(originalOut);

    // Get the captured output
    String output = outputStream.toString().trim();

    // Check if the result matches the simulated input
    // Ensure that the output includes the prompt and the input processing is correct
    assertEquals("Enter a number:", output);
  }
}
