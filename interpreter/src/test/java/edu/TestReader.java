package edu;

import edu.reader.Reader;
import edu.reader.ReaderResult;
import org.junit.jupiter.api.Test;

import java.util.Stack;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestReader {

  @Test
  public void testAddIdentifier() {
    Stack<String> identifiers = new Stack<>();
    Stack<Object> literals = new Stack<>();
    Reader reader = new Reader(new VariableContext(Map.of(), Map.of()), identifiers, literals);

    Reader updatedReader = reader.addIdentifier("x");
    assertTrue(updatedReader.hasIdentifier());
    assertEquals("x", updatedReader.getIdentifier().getValue());
  }

  @Test
  public void testAddLiteral() {
    Stack<String> identifiers = new Stack<>();
    Stack<Object> literals = new Stack<>();
    Reader reader = new Reader(new VariableContext(Map.of(), Map.of()), identifiers, literals);

    Reader updatedReader = reader.addLiteral(10);
    assertTrue(updatedReader.hasLiteral());
    assertEquals(10, updatedReader.getLiteral().getValue());
  }

  @Test
  public void testReadLiteral() {
    Stack<String> identifiers = new Stack<>();
    Stack<Object> literals = new Stack<>();
    literals.push(10);

    Reader reader = new Reader(new VariableContext(Map.of(), Map.of()), identifiers, literals);
    ReaderResult result = reader.read();

    assertEquals(10, result.getValue());
  }

  @Test
  public void testReadIdentifierAsNumber() {
    Stack<String> identifiers = new Stack<>();
    identifiers.push("x");

    Stack<Object> literals = new Stack<>();
    VariableContext variables = new VariableContext(Map.of("x", 10), Map.of());

    Reader reader = new Reader(variables, identifiers, literals);
    ReaderResult result = reader.read();

    assertEquals(10, result.getValue());
  }

  @Test
  public void testReadIdentifierAsString() {
    Stack<String> identifiers = new Stack<>();
    identifiers.push("y");

    Stack<Object> literals = new Stack<>();
    VariableContext variables = new VariableContext(Map.of(), Map.of("y", "Hello"));

    Reader reader = new Reader(variables, identifiers, literals);
    ReaderResult result = reader.read();

    assertEquals("Hello", result.getValue());
  }

  @Test
  public void testWriteNumberVariable() {
    Stack<String> identifiers = new Stack<>();
    Stack<Object> literals = new Stack<>();
    Reader reader = new Reader(new VariableContext(Map.of(), Map.of()), identifiers, literals);

    Reader updatedReader = reader.write("x", 10);
    assertEquals(10, updatedReader.getVariables().getNumberVariable("x"));
  }

  @Test
  public void testWriteStringVariable() {
    Stack<String> identifiers = new Stack<>();
    Stack<Object> literals = new Stack<>();
    Reader reader = new Reader(new VariableContext(Map.of(), Map.of()), identifiers, literals);

    Reader updatedReader = reader.write("y", "Hello");
    assertEquals("Hello", updatedReader.getVariables().getStringVariable("y"));
  }

  @Test
  public void testReadUndefinedIdentifierThrowsException() {
    Stack<String> identifiers = new Stack<>();
    identifiers.push("z");

    Stack<Object> literals = new Stack<>();
    Reader reader = new Reader(new VariableContext(Map.of(), Map.of()), identifiers, literals);

    Exception exception = assertThrows(RuntimeException.class, reader::read);
    assertEquals("Variable no definida: z", exception.getMessage());
  }

  @Test
  public void testWriteUnsupportedTypeThrowsException() {
    Stack<String> identifiers = new Stack<>();
    Stack<Object> literals = new Stack<>();
    Reader reader = new Reader(new VariableContext(Map.of(), Map.of()), identifiers, literals);

    Exception exception = assertThrows(RuntimeException.class, () -> reader.write("z", new Object()));
    assertEquals("Tipo de variable no soportado: class java.lang.Object", exception.getMessage());
  }
}
