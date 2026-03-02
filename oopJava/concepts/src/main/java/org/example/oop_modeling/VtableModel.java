package org.example.oop_modeling;

import java.util.HashMap;
import java.util.Map;

/**
 * Объект vtable java
 */
public class VtableModel {
    
    static Map<String, Object> newVtable() {
        // vtable
        Map<String, Object> vtable = new HashMap<>();

        // добавить реализацию в vtable
//        vtable.put(implName, implFunction);
        
        return vtable;
    }
    
    static void appendingImplementation() {}

    public static void main(String[] args) {
        var vtable = newVtable();
        
        
    }
}
