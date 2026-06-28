package com.nokhrin.interpreter.prop;

import com.nokhrin.interpreter.PropFileBaseVisitor;
import com.nokhrin.interpreter.PropFileParser;
import java.util.LinkedHashMap;
import java.util.Map;

public class PropFileVisitor extends PropFileBaseVisitor<Void> {
  Map<String, String> props = new LinkedHashMap<String, String>();

  public Void visitProp(PropFileParser.PropContext ctx) {
    String id = ctx.ID().getText();
    String value = ctx.STRING().getText();
    props.put(id, value);
    return null;
  }
}
