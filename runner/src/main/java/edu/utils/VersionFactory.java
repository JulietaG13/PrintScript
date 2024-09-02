package edu.utils;

import static edu.InterpreterFactory.createInterpreterV1;
import static edu.InterpreterFactory.createInterpreterV2;
import static edu.LexerFactory.createLexerV1;
import static edu.LexerFactory.createLexerV2;
import static edu.LinterFactory.createLinterV1;
import static edu.LinterFactory.createLinterV2;
import static edu.ParserFactory.createParserV1;
import static edu.ParserFactory.createParserV2;

import com.google.gson.JsonObject;
import edu.Formatter;
import edu.Interpreter;
import edu.Lexer;
import edu.Linter;
import edu.Parser;
import edu.rules.FormatterRuleProvider;
import java.util.Iterator;

public class VersionFactory {
  String version;

  public VersionFactory(String version) {
    this.version = version;
  }

  public Lexer createLexer(Iterator<String> text) {
    switch (version) {
      case "1.0":
        return createLexerV1(text);
      case "1.1":
        return createLexerV2(text);
      default:
        return null;
    }
  }

  public Parser createParser(Lexer lexer) {
    switch (version) {
      case "1.0":
        return createParserV1(lexer);
      case "1.1":
        return createParserV2(lexer);
      default:
        return null;
    }
  }

  public Interpreter createInterpreter(Parser parser) {
    switch (version) {
      case "1.0":
        return createInterpreterV1(parser);
      case "1.1":
        return createInterpreterV2(parser);
      default:
        return null;
    }
  }

  public Linter createLinter(JsonObject rules, Parser parser) {
    switch (version) {
      case "1.0":
        return createLinterV1(rules, parser);
      case "1.1":
        return createLinterV2(rules, parser);
      default:
        return null;
    }
  }

  public Formatter createFormatter(FormatterRuleProvider rules) {
    return new Formatter(rules);
  }
}
