package com.nokhrin.interpreter.miniscript;

import com.nokhrin.interpreter.MiniScriptBaseVisitor;
import com.nokhrin.interpreter.MiniScriptParser;
import com.nokhrin.interpreter.common.BoolValue;
import com.nokhrin.interpreter.common.DoubleValue;
import com.nokhrin.interpreter.common.ExprValue;
import com.nokhrin.interpreter.common.IntValue;
import com.nokhrin.interpreter.symbol_table.Scope;
import com.nokhrin.interpreter.symbol_table.Symbol;
import com.nokhrin.interpreter.symbol_table.VariableSymbol;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Objects;
import java.util.Optional;

import static com.nokhrin.interpreter.common.ArithmeticOperations.*;
import static com.nokhrin.interpreter.common.LogicalOperations.*;

public class MiniScriptEvalVisitor extends MiniScriptBaseVisitor<ExprValue> {
    private final Scope currentScope;

    public MiniScriptEvalVisitor(Scope currentScope) {
        this.currentScope = currentScope;
    }

    //region HELPERS

    private Optional<VariableSymbol> resolveVariable(String varName) {
        Symbol varSymbol = currentScope.resolve(varName);
        if (varSymbol instanceof VariableSymbol vs) return Optional.of(vs);
        return Optional.empty();
    }

    private VariableSymbol createVariable(String varName, ExprValue varValue) {
        Symbol.Type varType = switch (varValue) {
            case IntValue _ -> Symbol.Type.INT;
            case DoubleValue _ -> Symbol.Type.FLOAT;
            case BoolValue _ -> Symbol.Type.BOOLEAN;
        };
        VariableSymbol newVar = new VariableSymbol(varName, varType, currentScope);
        currentScope.define(newVar);
        return newVar;
    }
    //endregion HELPERS

    public ExprValue visitProg(MiniScriptParser.ProgContext ctx) {
        ExprValue statResult = null;
        for (MiniScriptParser.StatContext statContext : ctx.stat()) {
            statResult = visit(statContext);
        }
        return statResult;
    }

    //region ATOMS

    public ExprValue visitInt(MiniScriptParser.IntContext ctx) {
        return new IntValue(Long.parseLong(ctx.INT().getText()));
    }

    public ExprValue visitFloat(MiniScriptParser.FloatContext ctx) {
        return new DoubleValue(Double.parseDouble(ctx.FLOAT().getText()));
    }

    public ExprValue visitBool(MiniScriptParser.BoolContext ctx) {
        return new BoolValue(Boolean.parseBoolean(ctx.BOOL().getText()));
    }

    public ExprValue visitId(MiniScriptParser.IdContext ctx) {
        String varName = ctx.ID().getText();
        VariableSymbol varSymbol = currentScope.resolveSymbol(varName);
        return varSymbol.getValue();
    }

    //endregion ATOMS

    //region EXPRESSIONS

    public ExprValue visitParen(MiniScriptParser.ParenContext ctx) {
        return visit(ctx.expr());
    }

    public ExprValue visitAssign(MiniScriptParser.AssignContext ctx) {
        Objects.requireNonNull(ctx, "Precondition: ctx must not be null");
        assert ctx.ID() != null : "Precondition: ID required";
        assert ctx.expr() != null : "Precondition: expr required";

        String varName = ctx.ID().getText();
        ExprValue varValue = visit(ctx.expr());

        VariableSymbol varSymbol = resolveVariable(varName)
                .orElseGet(() -> createVariable(varName, varValue));
        varSymbol.setValue(varValue);
        return varValue;
    }

    public ExprValue visitOrExpr(MiniScriptParser.OrExprContext ctx) {
        ExprValue result = visit(ctx.andExpr(0));
        for (int i = 1; i < ctx.andExpr().size(); i++) {
            ExprValue rightExpr = visit(ctx.andExpr(i));
            result = or(result, rightExpr);
        }
        return result;
    }

    public ExprValue visitAndExpr(MiniScriptParser.AndExprContext ctx) {
        ExprValue result = visit(ctx.compExpr(0));
        for (int i = 1; i < ctx.compExpr().size(); i++) {
            ExprValue rightExpr = visit(ctx.compExpr(i));
            result = and(result, rightExpr);
        }
        return result;
    }

    public ExprValue visitCompExpr(MiniScriptParser.CompExprContext ctx) {
        ExprValue leftValue = visit(ctx.addSub(0));
        if (ctx.addSub().size() == 1) {
            return leftValue;
        }
        ExprValue rightValue = visit(ctx.addSub(1));
        TerminalNode opNode = (TerminalNode) ctx.getChild(1);
        return compare(leftValue, opNode.getText(), rightValue);
    }

    public ExprValue visitAddSub(MiniScriptParser.AddSubContext ctx) {
        Objects.requireNonNull(ctx, "Precondition: ctx must not be null");
        assert !ctx.mulDiv().isEmpty() : "Precondition: at least 1 expr required";

        ExprValue result = visit(ctx.mulDiv(0));
        for (int i = 1; i < ctx.mulDiv().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            ExprValue rightExpr = visit(ctx.mulDiv(i));
            result = switch (op) {
                case "+" -> add(result, rightExpr);
                case "-" -> sub(result, rightExpr);
                default -> result;
            };
        }
        return result;
    }

    public ExprValue visitMulDiv(MiniScriptParser.MulDivContext ctx) {
        Objects.requireNonNull(ctx, "Precondition: ctx must not be null");
        assert !ctx.unary().isEmpty() : "Precondition: at least 1 expr required";

        ExprValue result = visit(ctx.unary(0));
        for (int i = 1; i < ctx.unary().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            ExprValue rightExpr = visit(ctx.unary(i));
            result = switch (op) {
                case "*" -> mul(result, rightExpr);
                case "/" -> div(result, rightExpr);
                default -> result;
            };
        }
        return result;
    }

    //endregion EXPRESSIONS

    //region UNARY
    public ExprValue visitNot(MiniScriptParser.NotContext ctx) {
        return not(visit(ctx.unary()));
    }

    public ExprValue visitNeg(MiniScriptParser.NegContext ctx) {
        return neg(visit(ctx.unary()));
    }

    public ExprValue visitPos(MiniScriptParser.PosContext ctx) {
        return visit(ctx.unary());
    }


    //endregion UNARY
}
