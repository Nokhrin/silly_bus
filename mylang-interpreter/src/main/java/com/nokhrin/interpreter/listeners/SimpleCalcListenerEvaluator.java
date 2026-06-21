package com.nokhrin.interpreter.listeners;

import com.nokhrin.interpreter.SimpleCalcBaseListener;
import com.nokhrin.interpreter.SimpleCalcParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

public class SimpleCalcListenerEvaluator extends SimpleCalcBaseListener {
    ParseTreeProperty<Integer> values = new ParseTreeProperty<>();

    public void setValue(ParseTree node, int value) {
        values.put(node, value);
    }

    public int getValue(ParseTree node) {
        return values.get(node);
    }

    public void exitNum(SimpleCalcParser.NumContext ctx) {
        String intText = ctx.INT().getText();
        setValue(ctx, Integer.parseInt(intText));
    }

    public void exitAdd(SimpleCalcParser.AddContext ctx) {
        int left = getValue(ctx.expr(0));
        int right = getValue(ctx.expr(1));
        setValue(ctx, left + right);
    }

    public void exitMult(SimpleCalcParser.MultContext ctx) {
        int left = getValue(ctx.expr(0));
        int right = getValue(ctx.expr(1));
        setValue(ctx, left * right);
    }

    public void exitStat(SimpleCalcParser.StatContext ctx) {
        setValue(ctx, getValue(ctx.expr()));
    }
}
