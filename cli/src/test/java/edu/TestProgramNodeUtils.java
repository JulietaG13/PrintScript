package edu;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.ast.ProgramNode;
import edu.utils.CommandContext;
import edu.utils.FileReader;
import edu.utils.ProgramNodeUtils;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestProgramNodeUtils {

  private CommandContext commandContext;
  private ProgramNode mockProgramNode;

  @BeforeEach
  void setUp() {
    commandContext = new CommandContext();
  }

  @Test
  void testGetOrCreateProgramNode_ExistingNode() {
    // Given: The CommandContext already contains a ProgramNode
    String testFilePath = "src/test/java/resources/test.txt";
    String text = null;
    try {
      text = FileReader.readFileToString(testFilePath);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // When: getOrCreateProgramNode is called
    ProgramNode result = ProgramNodeUtils.getOrCreateProgramNode(commandContext, text);

    // Then: The existing ProgramNode should be returned
    assertNotNull(result);
  }
}
