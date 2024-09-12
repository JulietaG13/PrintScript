package edu.commands;

import static edu.progress.ProgressInputStreamWrapper.setProgress;

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
    InputStream input = setProgress(inputStream);
    Lexer lexer = versionFactory.createLexer(input);
    Parser parser = versionFactory.createParser(lexer);
    try {
      while (parser.hasNext()) {
        parser.next();
      }
    } catch (Exception e) {
      commandContext.setHasError(true);
    }
  }

  public CommandContext getCommandContext() {
    return commandContext;
  }
}
