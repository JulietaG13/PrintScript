package edu;

import com.github.ajalt.clikt.core.CliktCommand;
import edu.commands.AnalyzeCommand;
import edu.commands.CommandCreator;
import edu.commands.ExecuteCommand;
import edu.commands.FormatCommand;
import edu.commands.ValidateCommand;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestCommandCreator {
  @Test
  public void testCreateCommands() {
    List<CliktCommand> commands = CommandCreator.INSTANCE.createCommands();

    Assertions.assertFalse(commands.isEmpty(), "The list of commands should not be empty.");

    // Verificar que la lista contenga instancias de los comandos esperados
    Assertions.assertTrue(
        commands.get(0) instanceof ValidateCommand,
        "The first command should be an instance of ValidateCommand.");
    Assertions.assertTrue(
        commands.get(1) instanceof ExecuteCommand,
        "The second command should be an instance of ExecuteCommand.");
    Assertions.assertTrue(
        commands.get(2) instanceof FormatCommand,
        "The third command should be an instance of FormatCommand.");
    Assertions.assertTrue(
        commands.get(3) instanceof AnalyzeCommand,
        "The fourth command should be an instance of AnalyzeCommand.");
  }
}
