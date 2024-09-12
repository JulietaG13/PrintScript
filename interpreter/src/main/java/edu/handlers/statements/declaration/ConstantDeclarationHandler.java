package edu.handlers.statements.declaration;

import edu.ast.interfaces.StatementNode;
import edu.ast.statements.Kind;
import edu.ast.statements.VariableDeclarationNode;
import edu.context.ConstantContext;
import edu.exceptions.RuleFailedException;
import edu.handlers.StatementHandler;
import edu.helpers.DeclarationHelper;
import edu.inventory.Inventory;
import edu.reader.InterpreterReader;
import edu.rules.Rule;
import edu.utils.DeclarationHelperResult;
import edu.utils.HandlerResult;
import java.util.List;

public class ConstantDeclarationHandler implements StatementHandler {
  private final DeclarationHelper declarationHelper;

  public ConstantDeclarationHandler(List<Rule> rules) {
    this.declarationHelper = new DeclarationHelper(rules);
  }

  @Override
  public HandlerResult handle(
      StatementNode node, InterpreterReader interpreterReader, Inventory inventory)
      throws RuleFailedException {
    VariableDeclarationNode varNode = (VariableDeclarationNode) node;
    DeclarationHelperResult result =
        declarationHelper.handleDeclaration(varNode, interpreterReader, inventory);
    interpreterReader = result.getReader();
    inventory = result.getInventory();
    String varName = result.getVarName();

    if (varNode.kind() == Kind.CONST) {
      ConstantContext constantContext = inventory.getConstantContext();
      constantContext = constantContext.addConstant(varName);
      inventory = inventory.setConstantContext(constantContext);
    }
    return new HandlerResult(interpreterReader, inventory);
  }

  @Override
  public Class<? extends StatementNode> getHandledNodeClass() {
    return VariableDeclarationNode.class;
  }
}
