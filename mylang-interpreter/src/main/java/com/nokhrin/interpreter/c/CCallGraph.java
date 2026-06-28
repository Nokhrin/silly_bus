package com.nokhrin.interpreter.c;

import com.nokhrin.interpreter.CBaseListener;
import com.nokhrin.interpreter.CParser;
import org.antlr.v4.runtime.misc.MultiMap;
import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

public class CCallGraph {
    static class Graph {
        Set<String> nodes = new OrderedHashSet<>();
        MultiMap<String, String> edges = new MultiMap<>();

        public void edge(String source, String target) {
            nodes.add(source);
            nodes.add(target);
            edges.map(source, target);
        }

        public String toString() {
            return "edges: " + edges.toString() + ", functions: " + nodes;
        }

        public String toDOT() {
            StringBuilder buf = new StringBuilder();
            buf.append("digraph G {\n");
            buf.append("  ranksep=.25;\n");
            buf.append("  edge [arrowsize=.5]\n");
            buf.append("  node [shape=circle, fontname=\"ArialNarrow\",\n");
            buf.append("        fontsize=12, fixedsize=true, height=.45];\n");

            if (!nodes.isEmpty()) {
                buf.append("  ");
                for (String node : nodes) {
                    buf.append(node);
                    buf.append("; ");
                }
                buf.append("\n");
            }

            for (String src : edges.keySet()) {
                for (String trg : edges.get(src)) {
                    buf.append("  ");
                    buf.append(src);
                    buf.append(" -> ");
                    buf.append(trg);
                    buf.append(";\n");
                }
            }
            buf.append("}");
            return buf.toString();
        }

    }

    static class FunctionListener extends CBaseListener{
        Graph graph = new Graph();
        Deque<String> funcStack = new ArrayDeque<>();

        public void enterFuncDecl(CParser.FuncDeclContext ctx) {
            String funcName = ctx.ID().getText();
            graph.nodes.add(funcName);
            funcStack.push(funcName);
        }

        public void exitFuncDecl(CParser.FuncDeclContext ctx) {
            funcStack.pop();
        }

        public void exitCall(CParser.CallContext ctx) {
            String callee = ctx.ID().getText();
            graph.nodes.add(callee);

            if (!funcStack.isEmpty()) {
                String caller = funcStack.peek();
                graph.edge(caller, callee);
            } else {
                graph.nodes.add(callee);
            }
        }
    }
}
