package edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.utils.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class TestFileReader {

  @Test
  void testFileNotFound() {
    String nonExistentFilePath = "nonExistentFile.txt";
    assertThrows(
        IOException.class,
        () -> {
          FileReader.readFileToString(nonExistentFilePath);
        });
  }

  @Test
  void testReadFileToString() throws IOException {
    String filePath = "src/test/java/resources/test.txt";
    String expectedContent = "let a: Number = 5; println(a);" + System.lineSeparator();
    String content = FileReader.readFileToString(filePath);
    assertEquals(expectedContent, content);
  }
}
