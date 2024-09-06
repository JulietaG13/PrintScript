package edu;

import static edu.LexerFactory.createLexerV1;
import static edu.LexerFactory.createLexerV2;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.tokens.Token;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.Test;

public class LexerTest {
  private final String testDirectory = "src/test/resources/";

  private final List<String> files1 =
      List.of(
          "test1", "test9", "test10", "test11", "test12", "test13", "test14", "test15", "test16");
  private final List<String> files2 =
      List.of("test2", "test3", "test4", "test5", "test6", "test7", "test8");

  @Test
  public void testTokenizeV1() throws FileNotFoundException {
    for (String file : files1) {
      File srcFile = new File(testDirectory + file + "/" + "input.txt");
      File expectedOutput = new File(testDirectory + file + "/" + "output.txt");
      InputStream fileInputStream = new FileInputStream(srcFile);
      if (!srcFile.exists()) {
        throw new FileNotFoundException("File not found: " + srcFile.getAbsolutePath());
      }
      if (!expectedOutput.exists()) {
        throw new FileNotFoundException("File not found: " + expectedOutput.getAbsolutePath());
      }
      String expected = readFile(expectedOutput);
      String output = tokens(fileInputStream, createLexerV1(fileInputStream));
      assertEquals(expected, output);
    }
  }

  @Test
  public void testTokenizeV2() throws FileNotFoundException {
    for (String file : files2) {
      File srcFile = new File(testDirectory + file + "/" + "input.txt");
      File expectedOutput = new File(testDirectory + file + "/" + "output.txt");
      InputStream fileInputStream = new FileInputStream(srcFile);
      if (!srcFile.exists()) {
        throw new FileNotFoundException("File not found: " + srcFile.getAbsolutePath());
      }
      if (!expectedOutput.exists()) {
        throw new FileNotFoundException("File not found: " + expectedOutput.getAbsolutePath());
      }
      String expected = readFile(expectedOutput);
      String output = tokens(fileInputStream, createLexerV2(fileInputStream));
      assertEquals(expected, output);
    }
    for (String file : files1) {
      File srcFile = new File(testDirectory + file + "/" + "input.txt");
      File expectedOutput = new File(testDirectory + file + "/" + "output.txt");
      InputStream fileInputStream = new FileInputStream(srcFile);
      if (!srcFile.exists()) {
        throw new FileNotFoundException("File not found: " + srcFile.getAbsolutePath());
      }
      if (!expectedOutput.exists()) {
        throw new FileNotFoundException("File not found: " + expectedOutput.getAbsolutePath());
      }
      String expected = readFile(expectedOutput);
      String output = tokens(fileInputStream, createLexerV2(fileInputStream));
      assertEquals(expected, output);
    }
  }

  private String tokens(InputStream fileInputStream, Lexer lexer) {
    lexer.tokenize();
    String output =
        lexer.getTokens().stream()
            .map(Token::tokenString)
            .reduce("", (a, b) -> a + b + "\n")
            .trim();
    return output;
  }

  private String readFile(File file) throws FileNotFoundException {
    try {
      return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8).trim();
    } catch (IOException e) {
      throw new FileNotFoundException("Error reading file: " + file.getAbsolutePath());
    }
  }

  @Test
  public void testLexicalRangeToString() {
    LexicalRange range = new LexicalRange(15, 5, 1);
    assertEquals("LexicalRange(offset=15, line=5, column=1)", range.toString());
  }
}
