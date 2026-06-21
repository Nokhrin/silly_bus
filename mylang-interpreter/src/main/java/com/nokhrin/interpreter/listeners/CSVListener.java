package com.nokhrin.interpreter.listeners;

import com.nokhrin.interpreter.CSVBaseListener;
import com.nokhrin.interpreter.CSVParser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVListener extends CSVBaseListener {
    public static final String EMPTY = "";
    List<Map<String, String>> rows = new ArrayList<>();
    List<String> header;
    List<String> currRowFieldValues;

    public void exitStr(CSVParser.StrContext ctx){
        currRowFieldValues.add(ctx.STRING().getText());
    }
    public void exitPlain(CSVParser.PlainContext ctx) {
        currRowFieldValues.add(ctx.PLAIN().getText());
    }
    public void exitEmpty(CSVParser.EmptyContext ctx){
        currRowFieldValues.add(EMPTY);
    }
    public void exitHdr(CSVParser.HdrContext ctx){
        header=new ArrayList<String>();
        header.addAll(currRowFieldValues);
    }
    public void enterRow(CSVParser.RowContext ctx){
        currRowFieldValues=new ArrayList<String>();
    }
    public void exitRow(CSVParser.RowContext ctx){
        if (ctx.getParent().getRuleIndex() == CSVParser.RULE_hdr) return;

        Map<String,String> rowData = new LinkedHashMap<>();
        int i = 0;
        for (String value:currRowFieldValues){
            rowData.put(header.get(i), value);
            i++;
        }
        rows.add(rowData);
    }
}
