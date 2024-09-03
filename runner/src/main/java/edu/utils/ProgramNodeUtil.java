package edu.utils;

import edu.Lexer;
import edu.Parser;
import edu.ast.ProgramNode;
import java.io.InputStream;

public class ProgramNodeUtil {

  public static ProgramNode getProgramNode(InputStream inputStream, String version) {
    VersionFactory versionFactory = new VersionFactory(version);
    Lexer lexer = versionFactory.createLexer(inputStream);
    lexer.tokenize();
    Parser parser = versionFactory.createParser(lexer);
    return parser.parse(lexer.getTokens());
  }

  public static Parser getParser(InputStream inputStream, String version) {
    VersionFactory versionFactory = new VersionFactory(version);
    Lexer lexer = versionFactory.createLexer(inputStream);
    Parser parser = versionFactory.createParser(lexer);
    return parser;
  }
}
