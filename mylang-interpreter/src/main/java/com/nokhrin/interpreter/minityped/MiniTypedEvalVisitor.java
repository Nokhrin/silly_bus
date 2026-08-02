package com.nokhrin.interpreter.minityped;

import com.nokhrin.interpreter.MiniTypedBaseVisitor;
import com.nokhrin.interpreter.MiniTypedParser;
import com.nokhrin.interpreter.MiniTypedParser.*;
import com.nokhrin.interpreter.common.compiletime.*;
import com.nokhrin.interpreter.common.runtime.*;
import com.nokhrin.interpreter.common.values.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.nokhrin.interpreter.common.operations.ArithmeticOperations.*;
import static com.nokhrin.interpreter.common.operations.LogicalOperations.*;
import static com.nokhrin.interpreter.common.operations.TypeConversion.inferType;

public class MiniTypedEvalVisitor extends MiniTypedBaseVisitor<EvalResult> {
    private BaseScope currentScope;
    private final MethodRegistry methodRegistry;

    public MiniTypedEvalVisitor(BaseScope currentScope, MethodRegistry methodRegistry) {
        this.currentScope = currentScope;
        this.methodRegistry = methodRegistry;
    }

    //region HELPERS

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

    private EvalResult evaluateUserDefinedFunc(String funcName, CallExprContext ctx) {
        Symbol symbol = currentScope.resolve(funcName);
        if (!(symbol instanceof FunctionSymbol functionSymbol)) {
            throw new IllegalArgumentException("Function " + symbol.getName() + " is not defined");
        }

        FunctionBody body = methodRegistry.fetch(funcName);
        List<ExprValue> args = evaluateArguments(ctx);
        List<Parameter> parameters = functionSymbol.parameters();

        if (args.size() != parameters.size()) {
            throw new IllegalArgumentException("Fuction " + funcName
                    + ", args expected: " + parameters.size()
                    + ", args provided: " + args.size());
        }

        LocalScope localScope=new LocalScope(functionSymbol.scope(), funcName);

        Iterator<Parameter>parameterIterator=parameters.iterator();
        Iterator<ExprValue>argIterator=args.iterator();
        while (parameterIterator.hasNext() && argIterator.hasNext()){
            Parameter parameter=parameterIterator.next();
            ExprValue arg=argIterator.next();
            VariableSymbol paramSym=new VariableSymbol(parameter.name(), parameter.type(), localScope);
            localScope.define(paramSym);
            localScope.setValue(paramSym, arg);
        }
        return body.invoke(localScope);
    }

    //endregion HELPERS

    //region START
    public EvalResult visitProg(ProgContext ctx) {
        EvalResult statResult = new VoidValue();
        for (StatContext statContext : ctx.stat()) {
            statResult = visit(statContext);
        }
        return statResult;
    }
    //endregion

    //region ATOMS

    public EvalResult visitInt(IntContext ctx) {
        return new IntValue(Long.parseLong(ctx.INT().getText()));
    }

    public EvalResult visitFloat(FloatContext ctx) {
        return new DoubleValue(Double.parseDouble(ctx.FLOAT().getText()));
    }

    public EvalResult visitBool(BoolContext ctx) {
        return new BoolValue(Boolean.parseBoolean(ctx.BOOL().getText()));
    }

    public EvalResult visitId(IdContext ctx) {
        String varName = ctx.ID().getText();
        VariableSymbol varSymbol = (VariableSymbol) currentScope.resolve(varName);
        return currentScope.getValue(varSymbol);
    }

    //endregion ATOMS

    //region EXPRESSIONS

    public EvalResult visitParen(ParenContext ctx) {
        return visit(ctx.expr());
    }

    public EvalResult visitTernary(TernaryContext ctx) {
        EvalResult ifExpr = visit(ctx.or());

        if (ctx.ternary().size() < 2) {
            return ifExpr;
        }
        EvalResult thenExpr = visit(ctx.ternary(0));
        EvalResult elseExpr = visit(ctx.ternary(1));
        return conditionTrue(ifExpr) ? thenExpr : elseExpr;
    }

    public EvalResult visitOr(OrContext ctx) {
        EvalResult result = visit(ctx.and(0));
        for (int i = 1; i < ctx.and().size(); i++) {
            EvalResult rightExpr = visit(ctx.and(i));
            result = or(result, rightExpr);
        }
        return result;
    }

    public EvalResult visitAnd(AndContext ctx) {
        EvalResult result = visit(ctx.comp(0));
        for (int i = 1; i < ctx.comp().size(); i++) {
            EvalResult rightExpr = visit(ctx.comp(i));
            result = and(result, rightExpr);
        }
        return result;
    }

    public EvalResult visitComp(CompContext ctx) {
        EvalResult leftValue = visit(ctx.addSub(0));
        if (ctx.addSub().size() == 1) {
            return leftValue;
        }
        EvalResult rightValue = visit(ctx.addSub(1));
        TerminalNode opNode = (TerminalNode) ctx.getChild(1);
        return compare(leftValue, opNode.getText(), rightValue);
    }

    public EvalResult visitAddSub(AddSubContext ctx) {
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

    public EvalResult visitMulDiv(MulDivContext ctx) {
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
    public EvalResult visitNot(NotContext ctx) {
        return not(visit(ctx.unary()));
    }

    public EvalResult visitNeg(NegContext ctx) {
        return neg(visit(ctx.unary()));
    }

    public EvalResult visitPos(PosContext ctx) {
        return visit(ctx.unary());
    }
    //endregion UNARY

    //region STATEMENTS

    public EvalResult visitAssignStat(AssignStatContext ctx) {
        String varName = ctx.ID().getText();
        EvalResult varValue = visit(ctx.expr());
        Symbol symbol = currentScope.resolve(varName);
        VariableSymbol variableSymbol;

        if (symbol instanceof VariableSymbol varSym) {
            variableSymbol = varSym;
        } else {
            Symbol.Type varType = inferType(varValue);
            variableSymbol = new VariableSymbol(varName, varType, currentScope);
            currentScope.define(variableSymbol);
        }
        currentScope.setValue(variableSymbol, varValue);
        return varValue;
    }

    public EvalResult visitIfStat(IfStatContext ctx) {
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

    public EvalResult visitWhileStat(WhileStatContext ctx) {
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

    public EvalResult visitFuncCall(FuncCallContext ctx) {
        String funcName = ctx.callExpr().ID().getText();

        if (BuiltinFunctions.isBuiltin(funcName)) {
            return executeBuiltInFunc(funcName, ctx.callExpr());
        }
        return evaluateUserDefinedFunc(funcName, ctx.callExpr());
    }

    private EvalResult executeBuiltInFunc(String funcName, CallExprContext ctx) {
        BuiltinFunction builtinFunction = BuiltinFunctions.get(funcName);
        List<ExprValue> args = evaluateArguments(ctx);
        return builtinFunction.apply(args);
    }

    public EvalResult visitBreakStat(BreakStatContext ctx) {
        throw new BreakSignal();
    }

    public EvalResult visitContinueStat(ContinueStatContext ctx) {
        throw new ContinueSignal();
    }

    public EvalResult visitFuncDefinition(FuncDefinitionContext ctx) {
        String funcName = ctx.funcSignature().ID().getText();
        Symbol.Type returnType = resolveType(ctx.funcSignature().type().start.getType());
        List<Parameter> parameters = new ArrayList<>();

        if (ctx.funcSignature().funcParameters() != null) {
            for (FuncParameterContext parameterContext : ctx.funcSignature().funcParameters().funcParameter()) {
                parameters.add(new Parameter(parameterContext.ID().getText(), resolveType(parameterContext.type().start.getType())));
            }
        }

        BaseScope lexicalScope = currentScope;
        BlockContext blockContext = ctx.funcSignature().block();

        FunctionBody funcBody = (runtimeScope) -> {
            BaseScope callerScope = currentScope;
            currentScope = (BaseScope) runtimeScope;
            try {
                EvalResult statementResult = new VoidValue();
                for (StatContext statContext : blockContext.stat()) {
                    statementResult = visit(statContext);
                }
                return statementResult;
            } catch (ReturnSignal returnSignal) {
                return returnSignal.value;
            } finally {
                currentScope = callerScope;
            }
        };

        FunctionSymbol funcSymbol = new FunctionSymbol(funcName, parameters, returnType, lexicalScope);
        currentScope.define(funcSymbol);
        methodRegistry.register(funcName,funcBody);

        return new VoidValue();
    }

    private Symbol.Type resolveType(int tokenType) {
        return switch (tokenType) {
            case MiniTypedParser.INT_TYPE -> Symbol.Type.INTEGER;
            case MiniTypedParser.FLOAT_TYPE -> Symbol.Type.FLOAT;
            case MiniTypedParser.BOOL_TYPE -> Symbol.Type.BOOLEAN;
            case MiniTypedParser.VOID_TYPE -> Symbol.Type.VOID;
            default -> throw new IllegalStateException("Unexpected value: " + tokenType);
        };
    }

    public EvalResult visitBlock(BlockContext ctx) {
        EvalResult lastEvalResult = new VoidValue();
        for (StatContext statCtx : ctx.stat()) {
            lastEvalResult = visit(statCtx);
        }
        return lastEvalResult;
    }

    public EvalResult visitReturnStat(ReturnStatContext ctx) {
        EvalResult value = new VoidValue();
        if (ctx.expr() != null) {
            value = visit(ctx.expr());
        }
        throw new ReturnSignal(value);
    }
    //endregion
}
