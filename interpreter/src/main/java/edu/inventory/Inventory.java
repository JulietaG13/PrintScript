package edu.inventory;

import edu.context.ConstantContext;
import edu.context.Context;
import edu.context.TemporalContext;
import edu.context.VariableContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Inventory {
  private final List<Context> inventory;

  public Inventory(List<Context> inventory) {
    this.inventory = Collections.unmodifiableList(List.copyOf(inventory));
  }

  public VariableContext getVariableContext() {
    for (Context context : inventory) {
      if (context instanceof VariableContext) {
        return (VariableContext) context;
      }
    }
    throw new RuntimeException("Variable context not found");
  }

  public Inventory setVariableContext(VariableContext variableContext) {
    return updateContext(variableContext);
  }

  public ConstantContext getConstantContext() {
    for (Context context : inventory) {
      if (context instanceof ConstantContext) {
        return (ConstantContext) context;
      }
    }
    throw new RuntimeException("Constant context not found");
  }

  public Inventory setConstantContext(ConstantContext constantContext) {
    return updateContext(constantContext);
  }

  public TemporalContext getTemporaryContext() {
    for (Context context : inventory) {
      if (context instanceof TemporalContext) {
        return (TemporalContext) context;
      }
    }
    return null;
  }

  public Inventory createTemporaryContext() {
    TemporalContext temporalContext = new TemporalContext();
    return updateContext(temporalContext);
  }

  public Inventory setTemporaryContext(TemporalContext temporaryContext) {
    return updateContext(temporaryContext);
  }

  public Inventory removeTemporaryContext() {
    List<Context> newInventory = new ArrayList<>(inventory);
    newInventory.removeIf(context -> context instanceof TemporalContext);
    return new Inventory(newInventory);
  }

  private Inventory updateContext(Context newContext) {
    List<Context> newInventory = new ArrayList<>(inventory);
    for (int i = 0; i < newInventory.size(); i++) {
      if (newInventory.get(i).getClass().equals(newContext.getClass())) {
        newInventory.set(i, newContext);
        return new Inventory(newInventory);
      }
    }
    newInventory.add(newContext);
    return new Inventory(newInventory);
  }

  public List<Context> getContexts() {
    return inventory;
  }
}