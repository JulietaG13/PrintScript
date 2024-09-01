import edu.ast.ProgramNode;
import edu.commands.ExecutionCommand;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExecutionCommandTest {

  @Test
  public void testExecutionCommand() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      String testFilePath = "src/test/java/resources/input.txt";
      String version = "1.1";

      ExecutionCommand cmd = new ExecutionCommand(testFilePath, version);

      cmd.run();

      ProgramNode programNode = cmd.getCommandContext().getProgramNode();

      Assertions.assertNotNull(
        programNode, "El ProgramNode no debería ser nulo después de ejecutar ExecutionCommand.");

      Assertions.assertTrue(
        programNode.getBody() != null && !programNode.getBody().isEmpty(),
        "El ProgramNode debería tener al menos un statement.");

    } catch (IOException e) {
      Assertions.fail("Execution failed with IOException: " + e.getMessage());
    } finally {
      // Restaurar la salida estándar original
      System.setOut(originalOut);
    }
  }
}
