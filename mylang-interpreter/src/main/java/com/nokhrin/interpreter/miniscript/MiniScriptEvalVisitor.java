package com.nokhrin.interpreter.miniscript;

import com.nokhrin.interpreter.MiniScriptBaseVisitor;
import com.nokhrin.interpreter.MiniScriptParser;
import com.nokhrin.interpreter.common.*;
import com.nokhrin.interpreter.symbol_table.Scope;
import com.nokhrin.interpreter.symbol_table.Symbol;
import com.nokhrin.interpreter.symbol_table.VariableSymbol;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.nokhrin.interpreter.common.ArithmeticOperations.*;
import static com.nokhrin.interpreter.common.LogicalOperations.*;

public class MiniScriptEvalVisitor extends MiniScriptBaseVisitor<ExprValue> {
    private final Scope currentScope;

    public MiniScriptEvalVisitor(Scope currentScope) {
        this.currentScope = Objects.requireNonNull(currentScope, "Precondition: scope must not be null");
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
            default -> throw new IllegalStateException("Unexpected value: " + varValue);
        };
        VariableSymbol newVar = new VariableSymbol(varName, varType, currentScope);
        currentScope.define(newVar);
        return newVar;
    }

    private boolean conditionTrue(ExprValue result) {
        return result instanceof BoolValue(boolean value) && value;
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

    //region STATEMENTS

    public ExprValue visitAssign(MiniScriptParser.AssignContext ctx) {
        String varName = ctx.ID().getText();
        ExprValue varValue = visit(ctx.expr());

        VariableSymbol varSymbol = resolveVariable(varName)
                .orElseGet(() -> createVariable(varName, varValue));
        varSymbol.setValue(varValue);
        return varValue;
    }

    public ExprValue visitIfStat(MiniScriptParser.IfStatContext ctx){
        ExprValue condition = visit(ctx.expr());
        if (!(condition instanceof BoolValue(boolean value))){
            throw new IllegalArgumentException("Condition must be boolean expression. Provided: " + condition);
        }
        if (value) {
            // then branch
            return visit(ctx.stat(0));
        } else if (ctx.stat().size() > 1) {
            // else branch
            return visit(ctx.stat(1));
        }
        return null;
    }

    public ExprValue visitWhileStat(MiniScriptParser.WhileStatContext ctx) {
        while (conditionTrue(visit(ctx.expr()))){
            ExprValue whileBody = visit(ctx.stat());
            if (whileBody instanceof BreakSignal) {
                break;
            } else if (whileBody instanceof ContinueSignal) {
                continue;
            }
        }
        return null;
    }

    public ExprValue visitFuncCall(MiniScriptParser.FuncCallContext ctx){
        String funcName = ctx.ID().getText();

        if (!BuiltinFunctions.isBuiltin(funcName)) {
            throw new IllegalArgumentException("Unknown function: " + funcName);
        }

        BuiltinFunction builtinFunction= BuiltinFunctions.get(funcName);
        List<ExprValue> args = new ArrayList<>();

        if (ctx.exprList()!=null){
            for (MiniScriptParser.ExprContext argContext : ctx.exprList().expr()){
                EvalResult argResult = visit(argContext);
                if (!(argResult instanceof ExprValue argExpr)) {
                    throw new IllegalArgumentException("Expected argument type: ExprValue, got: " + argResult.getClass().getSimpleName());
                }
                args.add(argExpr);
            }
        }
        return builtinFunction.apply(args);
    }

    public ExprValue visitBreakStat(MiniScriptParser.BreakStatContext ctx) {
        return new BreakSignal();
    }

    public ExprValue visitContinueStat(MiniScriptParser.ContinueStatContext ctx){
        return new ContinueSignal();
    }
    //endregion
}
