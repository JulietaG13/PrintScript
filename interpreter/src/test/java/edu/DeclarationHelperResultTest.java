package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.context.VariableContext;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.utils.DeclarationHelperResult;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeclarationHelperResultTest {

  private InterpreterReader reader;
  private Inventory inventory;
  private String varName;
  private Object value;
  private DeclarationHelperResult result;

  @BeforeEach
  public void setUp() {
    reader = new InterpreterReader(new Stack<>(), new Stack<>());
    reader.addIdentifier("testIdentifier");
    inventory = new Inventory(List.of(new VariableContext(Map.of(), Map.of(), Map.of())));
    varName = "testVar";
    value = 42.0;

    result = new DeclarationHelperResult(reader, inventory, varName, value);
  }

  @Test
  public void testGetVarName() {
    assertEquals(varName, result.getVarName());
  }

  @Test
  public void testGetVarValue() {
    assertEquals(value, result.getVarValue());
  }

  @Test
  public void testGetReader() {
    assertEquals(reader, result.getReader());
  }

  @Test
  public void testGetInventory() {
    assertEquals(inventory, result.getInventory());
  }
}
