package edu.testrunner;

import edu.Formatter;
import edu.FormatterResult;
import edu.Lexer;
import edu.LexerFactory;
import edu.Parser;
import edu.ParserFactory;
import edu.rules.FormatterRuleProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TestRunner {

  public static Map<String, Object> runTest(
      TestCase testCase, String version, FormatterRuleProvider ruleProvider) {
    Map<String, Object> result = new HashMap<>();

    Lexer lexer;
    Parser parser;
    Formatter formatter;

    switch (version) {
      case "1.0":
        lexer = LexerFactory.createLexerV1(testCase.getInput());
        parser = ParserFactory.createParserV1(lexer);
        formatter = new Formatter(ruleProvider, parser);
        break;
      case "1.1":
        lexer = LexerFactory.createLexerV2(testCase.getInput());
        parser = ParserFactory.createParserV2(lexer);
        formatter = new Formatter(ruleProvider, parser);
        break;
      default:
        throw new RuntimeException("Unsupported version: " + version);
    }

    FormatterResult formatterResult = formatter.format();

    result.put("inputPath", testCase.getInputPath());
    result.put("expectedPath", testCase.getExpectedPath());
    result.put("actual", formatterResult.getResult());
    result.put("success", isSuccess(testCase.getExpected(), formatterResult.getResult()));

    System.out.println("--------------");
    System.out.println("Input Path: " + testCase.getInputPath());
    System.out.println("Expected Path: " + testCase.getExpectedPath());
    System.out.println("Actual Output: ");
    System.out.println(formatterResult.getResult());
    System.out.println("Success: " + result.get("success"));
    System.out.println("--------------");

    return result;
  }

  public static boolean isSuccess(Map<String, Object> result) {
    return (boolean) result.get("success");
  }

  private static boolean isSuccess(InputStream inputStream, String result) {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      StringBuilder inputContent = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        inputContent.append(line).append("\n"); // Append lines without \r
      }

      if (!inputContent.isEmpty()) {
        inputContent.setLength(inputContent.length() - 1);
      }

      String normalizedResult = result.replace("\r", "");

      return inputContent.toString().equals(normalizedResult);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
