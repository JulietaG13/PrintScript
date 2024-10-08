import static edu.FileReader.openFile;
import static org.junit.jupiter.api.Assertions.assertFalse;

import edu.ConsolePrinter;
import edu.commands.ExecutionCommand;
import edu.handlers.expressions.ConsoleInputProvider;
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

      ExecutionCommand cmd =
          new ExecutionCommand(
              openFile(testFilePath), version, new ConsoleInputProvider(), new ConsolePrinter());

      cmd.run();

      assertFalse(cmd.getCommandContext().hasError());

    } catch (IOException e) {
      Assertions.fail("Execution failed with IOException: " + e.getMessage());
    } finally {
      // Restaurar la salida estándar original
      System.setOut(originalOut);
    }
  }
}
