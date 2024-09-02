package edu.helpers;

import edu.ast.statements.Type;
import edu.ast.statements.VariableDeclarationNode;
import edu.context.TemporalContext;
import edu.context.VariableContext;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.reader.ReaderResult;
import edu.rules.Rule;
import edu.rules.RuleResult;
import edu.utils.DeclarationHelperResult;
import java.math.BigDecimal;
import java.util.List;

public class DeclarationHelper {
  private final List<Rule> rules;

  public DeclarationHelper(List<Rule> rules) {
    this.rules = rules;
  }

  public DeclarationHelperResult handleDeclaration(
      VariableDeclarationNode varNode, InterpreterReader interpreterReader, Inventory inventory) {

    // Aplicar reglas
    for (Rule rule : rules) {
      RuleResult result = rule.apply(varNode, interpreterReader, inventory);
      interpreterReader = result.reader();
      inventory = result.inventory();
    }

    // Obtener el identificador
    ReaderResult resultId = interpreterReader.getIdentifier();
    interpreterReader = resultId.getReader();
    String varName = resultId.getValue().toString();

    Object value = null;
    if (varNode.init() == null) {
      value = getDefaultForType(varNode.type());
    } else {
      // Obtener el valor
      ReaderResult resultValue = interpreterReader.getLiteral();
      interpreterReader = resultValue.getReader();
      value = resultValue.getValue();
    }

    // Guardar en el contexto adecuado (Temporal o Variable)
    if (inventory.getTemporaryContext() != null) {
      TemporalContext temporalContext = inventory.getTemporaryContext().storeValue(varName, value);
      inventory = inventory.setTemporaryContext(temporalContext);
    } else {
      VariableContext variableContext = inventory.getVariableContext().write(varName, value);
      inventory = inventory.setVariableContext(variableContext);
    }

    return new DeclarationHelperResult(interpreterReader, inventory, varName, value);
  }

  private Object getDefaultForType(Type type) {
    switch (type) {
      case NUMBER:
        return new BigDecimal(0);
      case STRING:
        return "";
      case BOOLEAN:
        return false;
      default:
        throw new RuntimeException("Unsupported type: " + type);
    }
  }
}
