package edu;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import edu.ast.ProgramNode;
import edu.utils.CommandContext;
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
  void testGetOrCreateProgramNode_ExistingNode() throws IOException {
    // Given: The CommandContext already contains a ProgramNode
    String testFilePath = "src/test/java/resources/test.txt";
    // When: getOrCreateProgramNode is called
    ProgramNode result = ProgramNodeUtils.getProgramNode(testFilePath);
    // Then: The existing ProgramNode should be returned
    assertNotNull(result);
  }

  @Test
  void testFileReaderInPrgmUtils() {
    String testFilePath = "src/test/java/resources/test.txt";
    try {
      String result = ProgramNodeUtils.getFileAsString(testFilePath);
      assertNotNull(result);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
