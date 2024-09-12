package edu.commands;

import static edu.progress.ProgressInputStreamWrapper.setProgress;

import com.google.gson.JsonObject;
import edu.Lexer;
import edu.Linter;
import edu.Parser;
import edu.Report;
import edu.utils.CommandContext;
import edu.utils.VersionFactory;
import java.io.InputStream;

public class AnalyzerCommand implements Command {
  private final CommandContext commandContext = new CommandContext();
  private final InputStream inputStream;
  private final JsonObject config;
  private final VersionFactory versionFactory;

  public AnalyzerCommand(InputStream inputStream, String version, JsonObject config) {
    this.inputStream = inputStream;
    this.config = config;
    this.versionFactory = new VersionFactory(version);
  }

  public void run() {
    InputStream input = setProgress(inputStream);
    Lexer lexer = versionFactory.createLexer(input);
    Parser parser = versionFactory.createParser(lexer);
    Linter linter = versionFactory.createLinter(config, parser);
    Report report = linter.analyze();
    commandContext.setLinterReport(report);
    System.out.println("Report: " + report.getReport());
  }

  public CommandContext getCommandContext() {
    return commandContext;
  }
}
