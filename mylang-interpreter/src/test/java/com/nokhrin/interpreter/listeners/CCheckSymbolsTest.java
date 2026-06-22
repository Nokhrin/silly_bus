package com.nokhrin.interpreter.listeners;

import org.testng.annotations.DataProvider;

public class CCheckSymbolsTest {
    @DataProvider(name = "srcToTree")
    public Object[][] getTestData() {
        return new Object[][]{
            {
                    """
int f(int x, float y) {
    g();   // forward reference is ok
    i = 3; // no declaration for i (error)
    g = 4; // g is not variable (error)
    return x + y; // x, y are defined, so no problem
}

void g() {
    int x = 0;
    float y;
    y = 9; // y is defined
    f();   // backward reference is ok
    z();   // no such function (error)
    y();   // y is not function (error)
    x = f; // f is not a variable (error)
}""",
                """
locals:[]
function<f:tINT>:[<x:tINT>, <y:tFLOAT>]
locals:[x, y]
function<g:tVOID>:[]
globals:[f, g]
line 3:4 no such variable: i
line 4:4 g is not a variable
line 13:4 no such function: z
line 14:4 y is not a function
line 15:8 f is not a variable"""
            }
        };
    }
}