package edu.handlers.statements;

import edu.ast.interfaces.StatementNode;
import edu.ast.statements.AssignmentNode;
import edu.context.VariableContext;
import edu.handlers.StatementHandler;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.reader.ReaderResult;
import edu.rules.Rule;
import edu.utils.HandlerResult;
import java.util.List;

public class AssignmentHandler implements StatementHandler {

  private final List<Rule> rules;

  public AssignmentHandler(List<Rule> rules) {
    this.rules = rules;
  }

  @Override
  public HandlerResult handle(
      StatementNode node, InterpreterReader interpreterReader, Inventory inventory) {
    AssignmentNode assignmentNode = (AssignmentNode) node;

    for (Rule rule : rules) {
      boolean ruleResult = rule.apply(node, interpreterReader, inventory);
      if (!ruleResult) {
        throw new RuntimeException(
            "Rule check failed for assignment: " + assignmentNode.id().name());
      }
    }

    ReaderResult result = interpreterReader.getIdentifier();
    String varName = result.getValue().toString();
    result = result.getReader().getLiteral();
    Object value = result.getValue();
    interpreterReader = result.getReader();
    VariableContext context = inventory.getVariableContext().write(varName, value);
    inventory = inventory.setVariableContext(context);

    return new HandlerResult(interpreterReader, inventory);
  }

  @Override
  public Class<? extends StatementNode> getHandledNodeClass() {
    return AssignmentNode.class;
  }
}
