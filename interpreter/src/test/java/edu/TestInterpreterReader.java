package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.reader.InterpreterReader;
import edu.reader.ReaderResult;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.junit.jupiter.api.Test;

public class TestInterpreterReader {

  @Test
  public void testAddIdentifier() {
    Stack<String> identifiers = new Stack<>();
    Stack<Object> literals = new Stack<>();
    InterpreterReader interpreterReader =
        new InterpreterReader(
            new VariableContext(Map.of(), Map.of(), Map.of(), Set.of()), identifiers, literals);

    InterpreterReader updatedInterpreterReader = interpreterReader.addIdentifier("x");
    assertTrue(updatedInterpreterReader.hasIdentifier());
    assertEquals("x", updatedInterpreterReader.getIdentifier().getValue());
  }

  @Test
  public void testAddLiteral() {
    Stack<String> identifiers = new Stack<>();
    Stack<Object> literals = new Stack<>();
    InterpreterReader interpreterReader =
        new InterpreterReader(
            new VariableContext(Map.of(), Map.of(), Map.of(), Set.of()), identifiers, literals);

    InterpreterReader updatedInterpreterReader = interpreterReader.addLiteral(10);
    assertTrue(updatedInterpreterReader.hasLiteral());
    assertEquals(10, updatedInterpreterReader.getLiteral().getValue());
  }

  @Test
  public void testReadLiteral() {
    Stack<String> identifiers = new Stack<>();
    Stack<Object> literals = new Stack<>();
    literals.push(10);

    InterpreterReader interpreterReader =
        new InterpreterReader(
            new VariableContext(Map.of(), Map.of(), Map.of(), Set.of()), identifiers, literals);
    ReaderResult result = interpreterReader.read();

    assertEquals(10, result.getValue());
  }

  @Test
  public void testReadIdentifierAsNumber() {
    Stack<String> identifiers = new Stack<>();
    identifiers.push("x");

    Stack<Object> literals = new Stack<>();
    VariableContext variables = new VariableContext(Map.of("x", 10), Map.of(), Map.of(), Set.of());

    InterpreterReader interpreterReader = new InterpreterReader(variables, identifiers, literals);
    ReaderResult result = interpreterReader.read();

    assertEquals(10, result.getValue());
  }

  @Test
  public void testReadIdentifierAsString() {
    Stack<String> identifiers = new Stack<>();
    identifiers.push("y");

    Stack<Object> literals = new Stack<>();
    VariableContext variables =
        new VariableContext(Map.of(), Map.of("y", "Hello"), Map.of(), Set.of());

    InterpreterReader interpreterReader = new InterpreterReader(variables, identifiers, literals);
    ReaderResult result = interpreterReader.read();

    assertEquals("Hello", result.getValue());
  }

  @Test
  public void testWriteNumberVariable() {
    Stack<String> identifiers = new Stack<>();
    Stack<Object> literals = new Stack<>();
    InterpreterReader interpreterReader =
        new InterpreterReader(
            new VariableContext(Map.of(), Map.of(), Map.of(), Set.of()), identifiers, literals);

    InterpreterReader updatedInterpreterReader = interpreterReader.write("x", 10);
    assertEquals(10, updatedInterpreterReader.getVariables().getNumberVariable("x"));
  }

  @Test
  public void testWriteStringVariable() {
    Stack<String> identifiers = new Stack<>();
    Stack<Object> literals = new Stack<>();
    InterpreterReader interpreterReader =
        new InterpreterReader(
            new VariableContext(Map.of(), Map.of(), Map.of(), Set.of()), identifiers, literals);

    InterpreterReader updatedInterpreterReader = interpreterReader.write("y", "Hello");
    assertEquals("Hello", updatedInterpreterReader.getVariables().getStringVariable("y"));
  }

  @Test
  public void testReadUndefinedIdentifierThrowsException() {
    Stack<String> identifiers = new Stack<>();
    identifiers.push("z");

    Stack<Object> literals = new Stack<>();
    InterpreterReader interpreterReader =
        new InterpreterReader(
            new VariableContext(Map.of(), Map.of(), Map.of(), Set.of()), identifiers, literals);

    Exception exception = assertThrows(RuntimeException.class, interpreterReader::read);
    assertEquals("Variable no definida: z", exception.getMessage());
  }

  @Test
  public void testWriteUnsupportedTypeThrowsException() {
    Stack<String> identifiers = new Stack<>();
    Stack<Object> literals = new Stack<>();
    InterpreterReader interpreterReader =
        new InterpreterReader(
            new VariableContext(Map.of(), Map.of(), Map.of(), Set.of()), identifiers, literals);

    Exception exception =
        assertThrows(RuntimeException.class, () -> interpreterReader.write("z", new Object()));
    assertEquals("Tipo de variable no soportado: class java.lang.Object", exception.getMessage());
  }

  @Test
  public void testWriteConstMarksVariableAsConstant() {
    VariableContext initialContext = new VariableContext(Map.of(), Map.of(), Map.of(), Set.of());
    InterpreterReader reader = new InterpreterReader(initialContext, new Stack<>(), new Stack<>());

    reader = reader.write("myVar", "Hello");
    assertFalse(reader.getVariables().isConstant("myVar"));

    reader = reader.writeConst("myVar");
    assertTrue(reader.getVariables().isConstant("myVar"));
  }

  @Test
  public void testWriteConstDoesNotModifyOtherVariables() {
    VariableContext initialContext =
        new VariableContext(Map.of("numVar", 10), Map.of(), Map.of(), Set.of());
    InterpreterReader reader = new InterpreterReader(initialContext, new Stack<>(), new Stack<>());

    reader = reader.writeConst("numVar");
    assertTrue(reader.getVariables().isConstant("numVar"));

    reader = reader.write("strVar", "Test");
    assertEquals("Test", reader.getVariables().getStringVariable("strVar"));
    assertFalse(reader.getVariables().isConstant("strVar"));
  }
}
