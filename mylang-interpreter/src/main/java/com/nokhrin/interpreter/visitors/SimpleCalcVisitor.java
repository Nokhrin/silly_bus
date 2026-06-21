package com.nokhrin.interpreter.visitors;

import com.nokhrin.interpreter.SimpleCalcBaseVisitor;
import com.nokhrin.interpreter.SimpleCalcParser;

public class SimpleCalcVisitor extends SimpleCalcBaseVisitor<Integer> {
    public Integer visitMult(SimpleCalcParser.MultContext ctx){
        return visit(ctx.expr(0)) * visit(ctx.expr(1));
    }

    public Integer visitAdd(SimpleCalcParser.AddContext ctx){
        return visit(ctx.expr(0)) + visit(ctx.expr(1));
    }

    public Integer visitNum(SimpleCalcParser.NumContext ctx){
        return Integer.valueOf(ctx.INT().getText());
    }

}
