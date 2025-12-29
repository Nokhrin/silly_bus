package command_parser.cli;

import command_parser.ParseResult;
import command_parser.Parser;

import java.util.Optional;

import static command_parser.Parser.parseCommand;

public class Main {
    public static void main(String[] args) {
        System.out.println(parseCommand("open", 0));
        //
        System.out.println(parseCommand("close 123", 0));
        //
    }
}