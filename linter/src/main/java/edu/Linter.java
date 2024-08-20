package edu;

import edu.ast.ProgramNode;
import edu.functions.And;
import edu.functions.FunctionRule;
import edu.identifiers.IdentifierType;
import edu.identifiers.Or;
import java.util.ArrayList;
import java.util.List;

public class Linter {
  private List<IdentifierType> identifierTypes;
  private List<FunctionRule> functionRules;

  private Linter(List<IdentifierType> identifierTypes, List<FunctionRule> functionRules) {
    this.identifierTypes = identifierTypes;
    this.functionRules = functionRules;
  }

  public Linter() {
    this.identifierTypes = new ArrayList<>();
    this.functionRules = new ArrayList<>();
  }

  public Linter addIdentifierType(IdentifierType identifierType) {
    List<IdentifierType> newIdentifierTypes = new ArrayList<>(this.identifierTypes);
    newIdentifierTypes.add(identifierType);
    return new Linter(newIdentifierTypes, this.functionRules);
  }

  public Linter addFunctionRule(FunctionRule functionRule) {
    List<FunctionRule> newFunctionRules = new ArrayList<>(this.functionRules);
    newFunctionRules.add(functionRule);
    return new Linter(this.identifierTypes, newFunctionRules);
  }

  public Report analyze(ProgramNode programNode) {
    Report report = new Report();
    FunctionRule combinedFunctionRule = combineFunctionRules();
    IdentifierType combinedIdentifierType = combineIdentifierTypes();
    StaticCodeAnalyzer analyzer =
        new StaticCodeAnalyzer(report, combinedIdentifierType, combinedFunctionRule);
    analyzer.visit(programNode);
    return report;
  }

  private IdentifierType combineIdentifierTypes() {
    return new Or(identifierTypes);
  }

  private FunctionRule combineFunctionRules() {
    return new And(functionRules);
  }
}
