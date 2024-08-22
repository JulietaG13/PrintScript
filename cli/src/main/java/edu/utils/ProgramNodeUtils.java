package edu.utils;

import edu.Lexer;
import edu.Parser;
import edu.ast.ProgramNode;

public class ProgramNodeUtils {
  public static ProgramNode getOrCreateProgramNode(
      CommandContext commandContext, String sourceFile) {
    if (commandContext.hasProgramNode()) {
      return commandContext.getProgramNode();
    } else {
      ProgramNode programNode = parseProgram(sourceFile);
      commandContext.setProgramNode(programNode);
      return programNode;
    }
  }

  private static ProgramNode parseProgram(String sourceFile) {
    Lexer lexer = new Lexer(sourceFile);
    lexer.tokenize();
    Parser parser = new Parser();
    return parser.parse(lexer.getTokens());
  }
}
