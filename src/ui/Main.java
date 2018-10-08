package ui;

import ast.Program;
import ast.exception.ASTNodeException;
import controller.exception.FileSystemNotSupportedException;
import libs.Tokenizer;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        List<String> literals = Arrays.asList("show", "move", "find", "file", "copy", "folder", "in", "to", "delete");
        if ((args.length != 0)  && (args[0].equals("cli"))) {
            launchApp(literals);
            return;
        }

        System.out.println("=============== DSL using input.txt ===============");
        Tokenizer.makeTokenizer("input.txt", literals);
        Program p = new Program();
        try {
            p.parse();
            p.evaluate();
        } catch (FileSystemNotSupportedException | FileNotFoundException
                | UnsupportedEncodingException | ASTNodeException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void launchApp(List<String> literals) {
        System.out.println("=============== DSL using CLI option ===============");
        System.out.println("Hello");
        Scanner scanner = new Scanner(System.in);
        String statement = "";
        String line;
        while (true) {
            if ((line = scanner.nextLine()).equalsIgnoreCase("quit")
                    || line.equalsIgnoreCase("bye")) {
                break;
            }

            if (line.endsWith(";")) {
                statement = statement.concat(line.substring(0, line.length() - 1));
                runProgram(statement, literals);
                // flush previous statement
                statement = "";
            } else {
                statement = statement.concat(line);
            }
        }
        System.out.println("BYE!");
    }

    private static void runProgram(String stmt, List<String> literals) {
        System.out.println("Running: " + stmt);
        Tokenizer.makeCliTokenizer(stmt, literals);
        Program p = new Program();

        try {
            p.parse();
            p.evaluate();
        } catch (FileSystemNotSupportedException | FileNotFoundException
                | UnsupportedEncodingException | ASTNodeException e) {
            System.out.println(e.getMessage());
        }
    }
}


