package com.nokhrin.interpreter.prop;

import com.nokhrin.interpreter.PropFileBaseListener;
import com.nokhrin.interpreter.PropFileParser;
import java.util.LinkedHashMap;
import java.util.Map;

public class PropFileListener extends PropFileBaseListener {
  Map<String, String> props = new LinkedHashMap<String, String>();

  public void exitProp(PropFileParser.PropContext ctx) {
    String id = ctx.ID().getText();
    String value = ctx.STRING().getText();
    props.put(id, value);
  }
}
