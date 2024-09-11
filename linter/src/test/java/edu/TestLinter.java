package edu;

import static edu.LexerFactory.createLexerV2;
import static edu.LinterFactory.createLinterV2;
import static edu.ParserFactory.createParserV2;
import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class TestLinter {
  private static final JsonObject nonExpression;
  private static final JsonObject snakeCase;
  private static final JsonObject upperCamelCase;
  private static final JsonObject lowerCamelCase;
  private static final JsonObject allCases;
  private static final JsonObject empty;
  private static final JsonObject nonExpressionPrint;

  static {
    nonExpression = new JsonObject();
    nonExpression.addProperty("identifier_format", "camel case");
    nonExpression.addProperty("mandatory-variable-or-literal-in-readInput", true);

    empty = new JsonObject();

    snakeCase = new JsonObject();
    snakeCase.addProperty("identifier_format", "snake case");

    upperCamelCase = new JsonObject();
    upperCamelCase.addProperty("identifier_format", "camel case");

    lowerCamelCase = new JsonObject();
    lowerCamelCase.addProperty("identifier_format", "camel case");

    allCases = new JsonObject();

    nonExpressionPrint = new JsonObject();
    nonExpressionPrint.addProperty("identifier_format", "camel case");
    nonExpressionPrint.addProperty("mandatory-variable-or-literal-in-println", true);
  }

  private static final String lineSeparator = System.lineSeparator();
  private final String testDirectory = "src/test/resources/";

  private final Map<String, JsonObject> files =
      Map.ofEntries(
          entry("readin-invalid-expr", nonExpression),
          entry("snake-case-valid", snakeCase),
          entry("upper-camel-case-valid", upperCamelCase),
          entry("upper-camel-case-valid2", empty),
          entry("lower-camel-case-valid", lowerCamelCase),
          entry("lower-camel-case-valid2", empty),
          entry("allcases-invalid", allCases),
          entry("allcases-invalid-if", allCases),
          entry("lower-camel-case-binary", allCases),
          entry("valid-camel-case-function", lowerCamelCase),
          entry("snake-case-valid-allcases", allCases));

  private final Map<String, JsonObject> files1 =
      Map.ofEntries(
          entry("allcases-invalid", allCases),
          entry("allcases-valid", allCases),
          entry("camel-case-valid", lowerCamelCase),
          entry("lower-camel-case-binary-valid", lowerCamelCase),
          entry("lower-camel-case-valid", lowerCamelCase),
          entry("snake-case-valid", snakeCase),
          entry("print-invalid-callexpr", nonExpressionPrint),
          entry("print-invalid-expr", nonExpressionPrint),
          entry("print-valid-identifier", nonExpressionPrint),
          entry("print-valid-nonexpression", nonExpressionPrint),
          entry("print-valid-norules", empty));

  @Test
  public void testLinterV2() throws FileNotFoundException {
    for (Map.Entry<String, JsonObject> testCase : files.entrySet()) {
      File srcFile = new File(testDirectory + "v2/" + testCase.getKey() + "/" + "input.txt");
      File expectedOutput =
          new File(testDirectory + "v2/" + testCase.getKey() + "/" + "output.txt");
      InputStream fileInputStream = new FileInputStream(srcFile);
      if (!srcFile.exists()) {
        throw new FileNotFoundException("File not found: " + srcFile.getAbsolutePath());
      }
      if (!expectedOutput.exists()) {
        throw new FileNotFoundException("File not found: " + expectedOutput.getAbsolutePath());
      }
      String expected = readFile(expectedOutput);
      String output =
          readReport(
              createLinterV2(testCase.getValue(), createParserV2(createLexerV2(fileInputStream))));
      assertEquals(expected.trim(), output.trim());
    }
    for (Map.Entry<String, JsonObject> testCase : files1.entrySet()) {
      File srcFile = new File(testDirectory + "v1/" + testCase.getKey() + "/" + "input.txt");
      File expectedOutput =
          new File(testDirectory + "v1/" + testCase.getKey() + "/" + "output.txt");
      InputStream fileInputStream = new FileInputStream(srcFile);
      if (!srcFile.exists()) {
        throw new FileNotFoundException("File not found: " + srcFile.getAbsolutePath());
      }
      if (!expectedOutput.exists()) {
        throw new FileNotFoundException("File not found: " + expectedOutput.getAbsolutePath());
      }
      String expected = readFile(expectedOutput);
      String output =
          readReport(
              createLinterV2(testCase.getValue(), createParserV2(createLexerV2(fileInputStream))));
      assertEquals(expected.trim(), output.trim());
    }
  }

  @Test
  public void testLinterV1() throws FileNotFoundException {
    for (Map.Entry<String, JsonObject> testCase : files1.entrySet()) {
      File srcFile = new File(testDirectory + "v1/" + testCase.getKey() + "/" + "input.txt");
      File expectedOutput =
          new File(testDirectory + "v1/" + testCase.getKey() + "/" + "output.txt");
      InputStream fileInputStream = new FileInputStream(srcFile);
      if (!srcFile.exists()) {
        throw new FileNotFoundException("File not found: " + srcFile.getAbsolutePath());
      }
      if (!expectedOutput.exists()) {
        throw new FileNotFoundException("File not found: " + expectedOutput.getAbsolutePath());
      }
      String expected = readFile(expectedOutput);
      String output =
          readReport(
              createLinterV2(testCase.getValue(), createParserV2(createLexerV2(fileInputStream))));
      assertEquals(expected.trim(), output.trim());
    }
  }

  private String readFile(File file) throws FileNotFoundException {
    try {
      return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8).trim();
    } catch (IOException e) {
      throw new FileNotFoundException("Error reading file: " + file.getAbsolutePath());
    }
  }

  private String readReport(Linter linter) {
    Report report = linter.analyze();
    Optional<List<String>> o = report.getReport();
    if (o.isEmpty()) {
      return "";
    }
    return o.get().stream().reduce("", (a, b) -> a + b + lineSeparator);
  }
}
