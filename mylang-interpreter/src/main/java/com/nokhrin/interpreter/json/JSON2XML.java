package com.nokhrin.interpreter.json;

import com.nokhrin.interpreter.JSONBaseListener;
import com.nokhrin.interpreter.JSONParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSON2XML extends JSONBaseListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(JSON2XML.class);
    private static final String EMPTY = "";
    ParseTreeProperty<String> xml = new ParseTreeProperty<>();

    String getXML(ParseTree ctx) {
        return xml.get(ctx);
    }

    void setXML(ParseTree ctx, String value) {
        xml.put(ctx, value);
    }

    public static String extract(String jsonValue) {
        if (jsonValue == null) {
            throw new IllegalArgumentException("Input must not be null");
        }
        if (jsonValue.length() < 2) {
            throw new IllegalArgumentException("String value must contain at least two quotes");
        }
        if (jsonValue.charAt(0) != '"' || jsonValue.charAt(jsonValue.length() - 1) != '"') {
            throw new IllegalArgumentException("String value must start and end with double quote");
        }
        return jsonValue.substring(1, jsonValue.length() - 1);
    }

    public void exitJson(JSONParser.JsonContext ctx) {
        if (ctx.value() != null) {
            setXML(ctx, getXML(ctx.value()));
            LOGGER.debug("NODE:  {} -> PROP: {}", ctx, getXML(ctx));
        }
    }

    public void exitAtomValue(JSONParser.AtomValueContext ctx) {
        setXML(ctx, ctx.getText());
        LOGGER.debug("NODE:  {} -> PROP: {}", ctx, getXML(ctx));
    }

    public void exitEmptyObject(JSONParser.EmptyObjectContext ctx) {
        setXML(ctx, EMPTY);
        LOGGER.debug("NODE:  {} -> PROP: {}", ctx, getXML(ctx));
    }

    public void exitNonEmptyObject(JSONParser.NonEmptyObjectContext ctx) {
        StringBuilder buffer = new StringBuilder();
        for (JSONParser.PairContext pairContext : ctx.pair()) {
            buffer.append(getXML(pairContext));
        }
        setXML(ctx, buffer.toString());
        LOGGER.debug("NODE:  {} -> PROP: {}", ctx, getXML(ctx));
    }

    public void exitObjectValue(JSONParser.ObjectValueContext ctx) {
        setXML(ctx, getXML(ctx.obj()));
        LOGGER.debug("NODE:  {} -> PROP: {}", ctx, getXML(ctx));
    }

    public void exitNonEmptyArray(JSONParser.NonEmptyArrayContext ctx) {
        StringBuilder buffer = new StringBuilder();
        for (JSONParser.ValueContext valueContext : ctx.value()) {
            buffer.append("<element>" + getXML(valueContext) + "</element>");
        }
        setXML(ctx, buffer.toString());
        LOGGER.debug("NODE:  {} -> PROP: {}", ctx, getXML(ctx));
    }

    public void exitEmptyArray(JSONParser.EmptyArrayContext ctx) {
        setXML(ctx, EMPTY);
    }

    public void exitPair(JSONParser.PairContext ctx) {
        String tag = extract(ctx.STR().getText());
        JSONParser.ValueContext valCtx = ctx.value();
        String xmlPair = String.format("<%s>%s</%s>\n", tag, getXML(valCtx), tag);
        setXML(ctx, xmlPair);
        LOGGER.debug("NODE:  {} -> PROP: {}", ctx, getXML(ctx));
    }

    public void exitStringValue(JSONParser.StringValueContext ctx) {
        setXML(ctx, extract(ctx.STR().getText()));
        LOGGER.debug("NODE:  {} -> PROP: {}", ctx, getXML(ctx));
    }

    public void exitArrayValue(JSONParser.ArrayValueContext ctx) {
        setXML(ctx, getXML(ctx.arr()));
        LOGGER.debug("NODE:  {} -> PROP: {}", ctx, getXML(ctx));
    }
}
