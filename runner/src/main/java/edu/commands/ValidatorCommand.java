package edu.commands;

import edu.Lexer;
import edu.Parser;
import edu.utils.CommandContext;
import edu.utils.VersionFactory;
import java.util.Iterator;

public class ValidatorCommand implements Command {

  private final CommandContext commandContext = new CommandContext();
  private final Iterator<String> fileReader;
  private final VersionFactory versionFactory;

  public ValidatorCommand(Iterator<String> fileReader, String version) {
    this.fileReader = fileReader;
    this.versionFactory = new VersionFactory(version);
  }

  public void run() {
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
