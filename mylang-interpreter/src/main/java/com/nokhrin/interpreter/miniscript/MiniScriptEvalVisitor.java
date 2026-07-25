package com.nokhrin.interpreter.miniscript;

import com.nokhrin.interpreter.MiniScriptBaseVisitor;
import com.nokhrin.interpreter.MiniScriptParser;
import com.nokhrin.interpreter.MiniScriptParser.*;
import com.nokhrin.interpreter.common.*;
import com.nokhrin.interpreter.symbol_table.LocalScope;
import com.nokhrin.interpreter.symbol_table.Scope;
import com.nokhrin.interpreter.symbol_table.Symbol;
import com.nokhrin.interpreter.symbol_table.VariableSymbol;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

import static com.nokhrin.interpreter.common.ArithmeticOperations.*;
import static com.nokhrin.interpreter.common.LogicalOperations.*;

public class MiniScriptEvalVisitor extends MiniScriptBaseVisitor<EvalResult> {
    private Scope currentScope;

    public MiniScriptEvalVisitor(Scope currentScope) {
        this.currentScope = Objects.requireNonNull(currentScope, "Precondition: scope must not be null");
    }

    //region HELPERS

    private Optional<VariableSymbol> resolveVariable(String varName) {
        Symbol varSymbol = currentScope.resolve(varName);
        if (varSymbol instanceof VariableSymbol vs) return Optional.of(vs);
        return Optional.empty();
    }

    private VariableSymbol createVariable(String varName, EvalResult varValue) {
        Symbol.Type varType = switch (varValue) {
            case IntValue _ -> Symbol.Type.INT;
            case DoubleValue _ -> Symbol.Type.FLOAT;
            case BoolValue _ -> Symbol.Type.BOOLEAN;
            case VoidValue _ -> Symbol.Type.VOID;
            case FuncValue _ -> Symbol.Type.FUNCTION;
        };
        VariableSymbol newVar = new VariableSymbol(varName, varType, currentScope);
        currentScope.define(newVar);
        return newVar;
    }

    private boolean conditionTrue(EvalResult result) {
        return result instanceof BoolValue(boolean value) && value;
    }

    private List<ExprValue> evaluateArguments(CallExprContext ctx) {
        List<ExprValue> argsBounded = new ArrayList<>();
        if (ctx.arguments() != null) {
            for (ExprContext argCtx : ctx.arguments().expr()) {
                EvalResult argResult = visit(argCtx);
                if (!(argResult instanceof ExprValue exprArg)) {
                    throw new IllegalArgumentException("Function argument " + argResult.getClass().getSimpleName() + " is not a value");
                }
                argsBounded.add(exprArg);
            }
        }
        return argsBounded;
    }

    private EvalResult evaluateUserDefinedFunc(String funcName, MiniScriptParser.CallExprContext ctx) {

        Symbol userDefinedFunc = currentScope.resolve(funcName);
        if (!(userDefinedFunc instanceof VariableSymbol funcSym)
                || !(funcSym.getValue()
                instanceof FuncValue func)) {
            throw new IllegalArgumentException("Undefined function: " + funcName);
        }

        LocalScope localScope = new LocalScope(func.enclosingScope(), func.name());

        List<String> params = func.parameters();
        List<ExprValue> args = evaluateArguments(ctx);
        for (int i = 0; i < params.size(); i++) {
            String paramName = params.get(i);
            ExprValue argValue = args.get(i);
            VariableSymbol paramSym = new VariableSymbol(
                    paramName, Symbol.inferType(argValue), localScope
            );
            paramSym.setValue(argValue);
            localScope.define(paramSym);
        }

        Scope parentScope = currentScope;
        currentScope = localScope;

        try {
            EvalResult lastResult = new VoidValue();
            for (MiniScriptParser.StatContext statInBody : func.body().stat()) {
                lastResult = visit(statInBody);
            }
            return lastResult;
        } catch (ReturnSignal returnSignal) {
            return returnSignal.value;
        } finally {
            currentScope = parentScope;
        }
    }

    //endregion HELPERS

    //region START
    public EvalResult visitProg(MiniScriptParser.ProgContext ctx) {
        EvalResult statResult = new VoidValue();
        for (MiniScriptParser.StatContext statContext : ctx.stat()) {
            statResult = visit(statContext);
        }
        return statResult;
    }
    //endregion

    //region ATOMS

    public EvalResult visitInt(MiniScriptParser.IntContext ctx) {
        return new IntValue(Long.parseLong(ctx.INT().getText()));
    }

    public EvalResult visitFloat(MiniScriptParser.FloatContext ctx) {
        return new DoubleValue(Double.parseDouble(ctx.FLOAT().getText()));
    }

    public EvalResult visitBool(MiniScriptParser.BoolContext ctx) {
        return new BoolValue(Boolean.parseBoolean(ctx.BOOL().getText()));
    }

    public EvalResult visitId(MiniScriptParser.IdContext ctx) {
        String varName = ctx.ID().getText();
        VariableSymbol varSymbol = currentScope.resolveSymbol(varName);
        return varSymbol.getValue();
    }

    //endregion ATOMS

    //region EXPRESSIONS

    public EvalResult visitParen(MiniScriptParser.ParenContext ctx) {
        return visit(ctx.expr());
    }

    public EvalResult visitOrExpr(MiniScriptParser.OrExprContext ctx) {
        EvalResult result = visit(ctx.andExpr(0));
        for (int i = 1; i < ctx.andExpr().size(); i++) {
            EvalResult rightExpr = visit(ctx.andExpr(i));
            result = or(result, rightExpr);
        }
        return result;
    }

    public EvalResult visitAndExpr(MiniScriptParser.AndExprContext ctx) {
        EvalResult result = visit(ctx.compExpr(0));
        for (int i = 1; i < ctx.compExpr().size(); i++) {
            EvalResult rightExpr = visit(ctx.compExpr(i));
            result = and(result, rightExpr);
        }
        return result;
    }

    public EvalResult visitCompExpr(MiniScriptParser.CompExprContext ctx) {
        EvalResult leftValue = visit(ctx.addSub(0));
        if (ctx.addSub().size() == 1) {
            return leftValue;
        }
        EvalResult rightValue = visit(ctx.addSub(1));
        TerminalNode opNode = (TerminalNode) ctx.getChild(1);
        return compare(leftValue, opNode.getText(), rightValue);
    }

    public EvalResult visitAddSub(MiniScriptParser.AddSubContext ctx) {
        EvalResult result = visit(ctx.mulDiv(0));
        for (int i = 1; i < ctx.mulDiv().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            EvalResult rightExpr = visit(ctx.mulDiv(i));
            result = switch (op) {
                case "+" -> add(result, rightExpr);
                case "-" -> sub(result, rightExpr);
                default -> result;
            };
        }
        return result;
    }

    public EvalResult visitMulDiv(MiniScriptParser.MulDivContext ctx) {
        EvalResult result = visit(ctx.unary(0));
        for (int i = 1; i < ctx.unary().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            EvalResult rightExpr = visit(ctx.unary(i));
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
    public EvalResult visitNot(MiniScriptParser.NotContext ctx) {
        return not(visit(ctx.unary()));
    }

    public EvalResult visitNeg(MiniScriptParser.NegContext ctx) {
        return neg(visit(ctx.unary()));
    }

    public EvalResult visitPos(MiniScriptParser.PosContext ctx) {
        return visit(ctx.unary());
    }
    //endregion UNARY

    //region STATEMENTS

    public EvalResult visitAssignStat(MiniScriptParser.AssignStatContext ctx) {
        String varName = ctx.ID().getText();
        EvalResult varValue = visit(ctx.expr());

        VariableSymbol varSymbol = resolveVariable(varName)
                .orElseGet(() -> createVariable(varName, varValue));
        varSymbol.setValue(varValue);
        return varValue;
    }

    public EvalResult visitIfStat(MiniScriptParser.IfStatContext ctx) {
        EvalResult condition = visit(ctx.expr());
        if (!(condition instanceof BoolValue(boolean value))) {
            throw new IllegalArgumentException("Condition must be boolean expression. Provided: " + condition);
        }
        if (value) {
            // then branch
            return visit(ctx.stat(0));
        } else if (ctx.stat().size() > 1) {
            // else branch
            return visit(ctx.stat(1));
        }
        return new VoidValue();
    }

    public EvalResult visitWhileStat(MiniScriptParser.WhileStatContext ctx) {
        while (conditionTrue(visit(ctx.expr()))) {
            try {
                visit(ctx.stat());

            } catch (BreakSignal breakSignal) {
                break;
            } catch (ContinueSignal continueSignal) {
            }
        }
        return new VoidValue();
    }

    public EvalResult visitFuncCall(MiniScriptParser.FuncCallContext ctx) {
        String funcName = ctx.callExpr().ID().getText();

        if (BuiltinFunctions.isBuiltin(funcName)) {
            return executeBuiltInFunc(funcName, ctx.callExpr());
        }
        return evaluateUserDefinedFunc(funcName, ctx.callExpr());
    }

    private EvalResult executeBuiltInFunc(String funcName, MiniScriptParser.CallExprContext ctx) {
        BuiltinFunction builtinFunction = BuiltinFunctions.get(funcName);
        List<ExprValue> args = evaluateArguments(ctx);
        return builtinFunction.apply(args);
    }

    public EvalResult visitBreakStat(MiniScriptParser.BreakStatContext ctx) {
        throw new BreakSignal();
    }

    public EvalResult visitContinueStat(MiniScriptParser.ContinueStatContext ctx) {
        throw new ContinueSignal();
    }

    public EvalResult visitFuncDef(MiniScriptParser.FuncDefContext ctx) {
        String funcName = ctx.funcSignature().ID().getText();
        List<String> funcParams = new ArrayList<>();
        if (ctx.funcSignature().parameters() != null) {
            for (TerminalNode paramCtx : ctx.funcSignature().parameters().ID()) {
                funcParams.add(paramCtx.getText());
            }
        }

        FuncValue func = new FuncValue(
                funcName,
                funcParams,
                ctx.funcSignature().block(),
                currentScope
        );

        VariableSymbol funcSymbol = new VariableSymbol(
                funcName, Symbol.Type.FUNCTION, currentScope
        );
        funcSymbol.setValue(func);
        currentScope.define(funcSymbol);

        return new VoidValue();
    }

    public EvalResult visitBlock(MiniScriptParser.BlockContext ctx) {
        EvalResult lastEvalResult = new VoidValue();
        for (StatContext statCtx : ctx.stat()) {
            lastEvalResult = visit(statCtx);
        }
        return lastEvalResult;
    }

    public EvalResult visitReturnStat(MiniScriptParser.ReturnStatContext ctx) {
        EvalResult value = visit(ctx.expr());
        throw new ReturnSignal(value);
    }
    //endregion
}
