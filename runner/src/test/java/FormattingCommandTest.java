import static edu.FileReader.openFile;

import edu.FormatterResult;
import edu.commands.FormattingCommand;
import edu.utils.CommandContext;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FormattingCommandTest {
  private final String lineSeparator = System.lineSeparator();

  @Test
  public void testFormattingCommandWithFile() throws IOException {
    String filePath = "src/test/java/resources/input.txt";
    String rulesPath = "src/test/java/resources/rules.txt";
    String expectedOutput = "let my_cool_variable : string = \"ciclon\";" + lineSeparator;

    FormattingCommand cmd = new FormattingCommand(openFile(filePath), "1.0", rulesPath);
    try {
      cmd.run();
    } catch (IOException e) {
      Assertions.fail("Execution failed with IOException: " + e.getMessage());
    }

    CommandContext commandContext = cmd.getCommandContext();

    FormatterResult formatterResult = commandContext.getFormatterResult();
    Assertions.assertNotNull(
        formatterResult,
        "El FormatterResult no debería ser nulo después de ejecutar FormattingCommand.");
    Assertions.assertEquals(
        expectedOutput,
        formatterResult.getResult(),
        "La salida del formatter debería coincidir con el resultado esperado.");
  }
}
