package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.context.VariableContext;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.reader.ReaderResult;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.junit.jupiter.api.Test;

public class TestInterpreterReader {

  @Test
  public void testAddIdentifier() {
    Stack<String> identifiers = new Stack<>();
    Stack<Object> literals = new Stack<>();
    InterpreterReader interpreterReader = new InterpreterReader(identifiers, literals);

    InterpreterReader updatedInterpreterReader = interpreterReader.addIdentifier("x");
    assertTrue(updatedInterpreterReader.hasIdentifier());
    assertEquals("x", updatedInterpreterReader.getIdentifier().getValue());
  }

  @Test
  public void testAddLiteral() {
    Stack<String> identifiers = new Stack<>();
    Stack<Object> literals = new Stack<>();
    InterpreterReader interpreterReader = new InterpreterReader(identifiers, literals);

    InterpreterReader updatedInterpreterReader = interpreterReader.addLiteral(10);
    assertTrue(updatedInterpreterReader.hasLiteral());
    assertEquals(10, updatedInterpreterReader.getLiteral().getValue());
  }

  @Test
  public void testReadLiteral() {
    Stack<String> identifiers = new Stack<>();
    Stack<Object> literals = new Stack<>();
    literals.push(10);

    InterpreterReader interpreterReader = new InterpreterReader(identifiers, literals);
    ReaderResult result = interpreterReader.getLiteral();

    assertEquals(10, result.getValue());
  }

  @Test
  public void testReadIdentifierAsNumber() {
    Stack<String> identifiers = new Stack<>();
    identifiers.push("x");

    Stack<Object> literals = new Stack<>();
    VariableContext variables =
        new VariableContext(Map.of("x", new BigDecimal(10)), Map.of(), Map.of());
    Inventory inventory = new Inventory(new ArrayList<>(List.of(variables)));
    InterpreterReader interpreterReader = new InterpreterReader(identifiers, literals);
    ReaderResult result = interpreterReader.read(inventory);

    assertEquals(new BigDecimal(10), result.getValue());
  }
}
