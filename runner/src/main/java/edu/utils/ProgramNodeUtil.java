package edu.utils;

import edu.Lexer;
import edu.Parser;
import edu.ast.ProgramNode;
import java.util.Iterator;

public class ProgramNodeUtil {

  public static ProgramNode getProgramNode(Iterator<String> fileReader, String version) {
    VersionFactory versionFactory = new VersionFactory(version);
    Lexer lexer = versionFactory.createLexer(fileReader);
    lexer.tokenize();
    Parser parser = versionFactory.createParser(lexer);
    return parser.parse(lexer.getTokens());
  }

  public static Parser getParser(Iterator<String> fileReader, String version) {
    VersionFactory versionFactory = new VersionFactory(version);
    Lexer lexer = versionFactory.createLexer(fileReader);
    Parser parser = versionFactory.createParser(lexer);
    return parser;
  }
}
