package com.nokhrin.interpreter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    static void main(String[] args) throws IOException {
        if (args.length > 0) {
            String text = Files.readString(Path.of(args[0]));
            new Repl().runSource(text);
        } else {
            new Repl().run();
        }
    }
}
