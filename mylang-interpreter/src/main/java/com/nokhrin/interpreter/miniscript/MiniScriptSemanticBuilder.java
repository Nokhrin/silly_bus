package com.nokhrin.interpreter.miniscript;

import com.nokhrin.interpreter.MiniScriptBaseListener;
import com.nokhrin.interpreter.MiniScriptParser;
import com.nokhrin.interpreter.common.compiletime.Scope;
import com.nokhrin.interpreter.common.compiletime.Symbol;
import com.nokhrin.interpreter.common.runtime.GlobalScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MiniScriptSemanticBuilder extends MiniScriptBaseListener {
    static final Logger LOGGER = LoggerFactory.getLogger(MiniScriptSemanticBuilder.class);

    private GlobalScope globalScope;

    public Scope getGlobalScope() {
        return globalScope;
    }

    private Symbol.Type resolveType(int tokenType) {
        return switch (tokenType) {
            case MiniScriptParser.INT -> Symbol.Type.INTEGER;
            case MiniScriptParser.FLOAT -> Symbol.Type.FLOAT;
            case MiniScriptParser.BOOL -> Symbol.Type.BOOLEAN;
            case MiniScriptParser.VOID -> Symbol.Type.VOID;
            default -> throw new IllegalArgumentException("Unknown type token: " + tokenType);
        };
    }

    public void enterProg(MiniScriptParser.ProgContext ctx) {
        globalScope = new GlobalScope();
        LOGGER.debug("Successfully created Global Scope: {}", globalScope);
    }
}