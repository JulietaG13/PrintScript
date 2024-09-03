import static edu.FileReader.openFile;

import com.google.gson.JsonObject;
import edu.commands.AnalyzerCommand;
import edu.utils.CommandContext;
import edu.utils.JsonConfigLoader;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnalyzerCommandTest {

  private JsonObject getRules(String configFile) throws IOException {
    return JsonConfigLoader.loadFromFile(configFile);
  }

  @Test
  void testAnalyzeCommandWithFile() throws IOException {
    String rulesPath = "src/test/java/resources/analysis_rules.txt";
    String filePath = "src/test/java/resources/analysis_input.txt";

    String expectedOutput =
        "Error in println function: The println "
            + "function only accepts identifiers or literals as arguments.\n"
            + "Argument 1 is invalid:\n"
            + " - Type: BinaryExpressionNode\n"
            + " - Position: LexicalRange(offset=8, line=0, column=8)\n"
            + " - Content: \"Hello\" + \"World\"";

    AnalyzerCommand analyzerCommand =
        new AnalyzerCommand(openFile(filePath), "1.0", getRules(rulesPath));

    analyzerCommand.run();

    CommandContext commandContext = analyzerCommand.getCommandContext();

    edu.Report report = commandContext.getLinterReport();
    Assertions.assertNotNull(report, "The report should not be null");
    Assertions.assertEquals(1, report.getMessages().size(), "There should be one error message");
    Assertions.assertEquals(
        expectedOutput,
        report.getMessages().get(0),
        "The error message does not match the expected output");
  }
}
