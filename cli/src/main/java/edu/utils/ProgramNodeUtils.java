package edu.utils;

import static edu.LexerFactory.createLexerV1;

import edu.Lexer;
import edu.Parser;
import edu.ast.ProgramNode;
import java.io.IOException;

public class ProgramNodeUtils {

  public static ProgramNode getProgramNode(String sourceFile) throws IOException {
    String text = getFileAsString(sourceFile);
    Lexer lexer = createLexerV1(text);
    lexer.tokenize();
    Parser parser = new Parser();
    return parser.parse(lexer.getTokens());
  }

  public static String getFileAsString(String sourceFile) throws IOException {
    return FileReader.readFileToString(sourceFile);
  }
}
