package com.nokhrin.interpreter.common;

import com.nokhrin.interpreter.common.runtime.BuiltinFunctions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BuiltinFunctionsTest {

    @DataProvider
    private Object[][] knownBuiltIns() {
        return new Object[][] {
            {"print"},
            {"sin"},
            {"abs"},
            {"pow"},
        };
    }

    @DataProvider
    private Object[][] unknownBuiltIns() {
        return new Object[][] {
            {"qwerty"},
            {"cos"},
            {"impl"},
            {"sqrt"},
        };
    }

    @Test(dataProvider = "knownBuiltIns")
    void isBuiltIn_knownFuncs_returnsTrue(String funcName){
        assertTrue(BuiltinFunctions.isBuiltin(funcName));
    }

    @Test(dataProvider = "knownBuiltIns")
    void get_knownFuncs_returnsNonNull(String funcName){
        assertNotNull(BuiltinFunctions.get(funcName));
    }

    @Test(dataProvider = "unknownBuiltIns")
    void isBuiltIn_unknownFuncs_returnsTrue(String funcName){
        assertFalse(BuiltinFunctions.isBuiltin(funcName));
    }

    @Test(dataProvider = "unknownBuiltIns")
    void get_unknownFuncs_returnsNull(String funcName){
        assertNull(BuiltinFunctions.get(funcName));
    }

}