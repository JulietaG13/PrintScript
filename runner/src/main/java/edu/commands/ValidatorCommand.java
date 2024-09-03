package edu.commands;

import edu.Lexer;
import edu.Parser;
import edu.utils.CommandContext;
import edu.utils.VersionFactory;
import java.io.InputStream;

public class ValidatorCommand implements Command {

  private final CommandContext commandContext = new CommandContext();
  private final InputStream inputStream;
  private final VersionFactory versionFactory;

  public ValidatorCommand(InputStream inputStream, String version) {
    this.inputStream = inputStream;
    this.versionFactory = new VersionFactory(version);
  }

  public void run() {
    Lexer lexer = versionFactory.createLexer(inputStream);
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
