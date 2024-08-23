package edu.commands;

import edu.Lexer;
import edu.Parser;
import edu.ast.ProgramNode;
import edu.tokens.Token;
import edu.utils.CommandContext;
import edu.utils.FileReader;
import java.io.IOException;
import java.util.List;

public class ValidatorCommand implements Command {
  private final String sourceFile;
  private final CommandContext commandContext;

  public ValidatorCommand(String sourceFile, CommandContext commandContext) {
    this.sourceFile = sourceFile;
    this.commandContext = commandContext;
  }

  @Override
  public void execute() {
    try {
      String text = getText();

      Lexer lexer = new Lexer(text);
      lexer.tokenize();

      List<Token> tokens = lexer.getTokens();

      Parser parser = new Parser();
      ProgramNode programNode = parser.parse(tokens);
      commandContext.setProgramNode(programNode);

    } catch (IOException e) {
      System.err.println("Error al ejecutar la operación: " + e.getMessage());
    }
  }

  private String getText() throws IOException {
    FileReader reader = new FileReader();
    try {
      return reader.readFileToString(this.sourceFile);
    } catch (IOException e) {
      System.err.println("Error al leer el archivo: " + e.getMessage());
      throw e;
    }
  }
}
