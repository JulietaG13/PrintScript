package edu.commands;

import static edu.FileReader.openFile;

import com.google.gson.JsonObject;
import edu.Lexer;
import edu.Linter;
import edu.Parser;
import edu.Report;
import edu.utils.CommandContext;
import edu.utils.JsonConfigLoader;
import edu.utils.VersionFactory;
import java.io.IOException;

public class AnalyzerCommand implements Command {
  private final CommandContext commandContext = new CommandContext();
  private final String sourceFile;
  private final String version;
  private final String configFile;
  private final VersionFactory versionFactory;

  public AnalyzerCommand(String sourceFile, String version, String configFile) {
    this.sourceFile = sourceFile;
    this.version = version;
    this.configFile = configFile;
    this.versionFactory = new VersionFactory(version);
  }

  public void run() throws IOException {
    System.out.println("Reading file");
    Lexer lexer = versionFactory.createLexer(openFile(sourceFile));
    Parser parser = versionFactory.createParser(lexer);
    System.out.println("Parsing completed");
    Linter linter = getLinter(parser);
    System.out.println("Analyzing program");
    Report report = linter.analyze();
    System.out.println("Analysis completed");
    commandContext.setLinterReport(report);
    System.out.println("Report: " + report.getReport());
  }

  private Linter getLinter(Parser parser) throws IOException {
    JsonObject config = JsonConfigLoader.loadFromFile(configFile);
    return versionFactory.createLinter(config, parser);
  }

  public CommandContext getCommandContext() {
    return commandContext;
  }
}
