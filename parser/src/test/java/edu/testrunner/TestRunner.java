package edu.testrunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import edu.Lexer;
import edu.LexerFactory;
import edu.Parser;
import edu.ParserFactory;
import edu.ast.interfaces.StatementNode;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRunner {

  public static Map<String, Object> runTest(TestCase testCase, String version) {
    Map<String, Object> result = new HashMap<>();

    Lexer lexer;
    Parser parser;

    switch (version) {
      case "1.0":
        lexer = LexerFactory.createLexerV1(testCase.getInput());
        parser = ParserFactory.createParserV1(lexer);
        break;
      case "1.1":
        lexer = LexerFactory.createLexerV2(testCase.getInput());
        parser = ParserFactory.createParserV2(lexer);
        break;
      default:
        throw new RuntimeException("Unsupported version: " + version);
    }

    List<StatementNode> statements = new ArrayList<>();
    while (parser.hasNext()) {
      statements.add(parser.next());
    }

    JsonObject actual = listToJson(statements);

    result.put("inputPath", testCase.getInputPath());
    result.put("expectedPath", testCase.getExpectedPath());
    result.put("actual", actual);
    result.put("success", isSuccess(testCase.getExpected(), actual));

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    System.out.println("--------------");
    System.out.println("Input Path: " + testCase.getInputPath());
    System.out.println("Expected Path: " + testCase.getExpectedPath());
    System.out.println("Actual Output: ");
    System.out.println(gson.toJson(actual));
    System.out.println("Success: " + result.get("success"));
    System.out.println("--------------");

    return result;
  }

  public static boolean isSuccess(Map<String, Object> result) {
    return (boolean) result.get("success");
  }

  public static JsonObject listToJson(List<StatementNode> statements) {
    Gson gson = new Gson();
    JsonArray jsonArray = new JsonArray();

    for (StatementNode statement : statements) {
      JsonElement jsonElement = gson.toJsonTree(statement);
      jsonArray.add(jsonElement);
    }

    JsonObject jsonObject = new JsonObject();
    jsonObject.add("statements", jsonArray);
    return jsonObject;
  }

  private static boolean isSuccess(InputStream inputStream, JsonObject actual) {
    JsonObject expected = new JsonObject();

    try (InputStreamReader reader = new InputStreamReader(inputStream)) {
      expected = JsonParser.parseReader(reader).getAsJsonObject();
    } catch (IOException e) {
      System.err.println("Error reading JSON from InputStream: " + e.getMessage());
      return false;
    } catch (Exception e) {
      System.err.println("Error parsing JSON: " + e.getMessage());
      return false;
    }

    return actual != null && actual.equals(expected);
  }
}
