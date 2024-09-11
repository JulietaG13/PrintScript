package edu.helpers;

import edu.context.Context;
import edu.context.VariableContext;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.reader.ReaderResult;
import java.util.Optional;

public class ReaderHelper {

  public Optional<ReaderResult> findVariableInContexts(
      Inventory inventory, String id, InterpreterReader reader) {

    return inventory.getContexts().stream()
        .map(context -> findInContext(context, id, reader))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .findFirst();
  }

  private Optional<ReaderResult> findInContext(
      Context context, String id, InterpreterReader reader) {
    if (context instanceof VariableContext) {
      return findInVariableContext((VariableContext) context, id, reader);
    }
    return Optional.empty();
  }

  private Optional<ReaderResult> findInVariableContext(
      VariableContext variableContext, String id, InterpreterReader reader) {
    if (variableContext.hasStringVariable(id)) {
      return Optional.of(new ReaderResult(reader, variableContext.getStringVariable(id)));
    } else if (variableContext.hasNumberVariable(id)) {
      return Optional.of(new ReaderResult(reader, variableContext.getNumberVariable(id)));
    } else if (variableContext.hasBooleanVariable(id)) {
      return Optional.of(new ReaderResult(reader, variableContext.getBooleanVariable(id)));
    }
    return Optional.empty();
  }
}
