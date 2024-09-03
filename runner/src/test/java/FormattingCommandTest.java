import static edu.FileReader.openFile;

import com.google.gson.JsonObject;
import edu.FormatterResult;
import edu.commands.FormattingCommand;
import edu.utils.CommandContext;
import edu.utils.JsonConfigLoader;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FormattingCommandTest {
  private final String lineSeparator = System.lineSeparator();

  private JsonObject getRules(String configFile) throws IOException {
    return JsonConfigLoader.loadFromFile(configFile);
  }

  @Test
  public void testFormattingCommandWithFile() throws IOException {
    String filePath = "src/test/java/resources/input.txt";
    String rulesPath = "src/test/java/resources/rules.txt";
    String expectedOutput = "let my_cool_variable : string = \"ciclon\";" + lineSeparator;

    FormattingCommand cmd = new FormattingCommand(openFile(filePath), "1.0", getRules(rulesPath));
    cmd.run();

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
