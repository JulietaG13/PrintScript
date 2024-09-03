package edu.commands;

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
  private final String version;
  private final JsonObject config;
  private final VersionFactory versionFactory;

  public AnalyzerCommand(InputStream inputStream, String version, JsonObject config) {
    this.inputStream = inputStream;
    this.version = version;
    this.config = config;
    this.versionFactory = new VersionFactory(version);
  }

  public void run() {
    System.out.println("Reading file");
    Lexer lexer = versionFactory.createLexer(inputStream);
    Parser parser = versionFactory.createParser(lexer);
    System.out.println("Parsing completed");
    Linter linter = versionFactory.createLinter(config, parser);
    System.out.println("Analyzing program");
    Report report = linter.analyze();
    System.out.println("Analysis completed");
    commandContext.setLinterReport(report);
    System.out.println("Report: " + report.getReport());
  }

  public CommandContext getCommandContext() {
    return commandContext;
  }
}
