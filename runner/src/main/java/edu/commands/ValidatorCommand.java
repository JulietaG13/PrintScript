package edu.commands;

import static edu.FileReader.openFile;

import edu.Lexer;
import edu.Parser;
import edu.utils.CommandContext;
import edu.utils.VersionFactory;
import java.io.IOException;
import java.util.Iterator;

public class ValidatorCommand implements Command {

  private final CommandContext commandContext = new CommandContext();
  private final String sourceFile;
  private final VersionFactory versionFactory;

  public ValidatorCommand(String sourceFile, String version) {
    this.sourceFile = sourceFile;
    this.versionFactory = new VersionFactory(version);
  }

  public void run() throws IOException {
    Iterator<String> fileReader = openFile(sourceFile);
    Lexer lexer = versionFactory.createLexer(fileReader);
    lexer.tokenize();
    var tokens = lexer.getTokens();
    Parser parser = versionFactory.createParser(lexer);
    var programNode = parser.parse(tokens);
    commandContext.setProgramNode(programNode);
  }

  public CommandContext getCommandContext() {
    return commandContext;
  }
}
