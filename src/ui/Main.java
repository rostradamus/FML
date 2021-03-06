package ui;

import ast.Program;
import ast.exception.ASTNodeException;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.Tokenizer;
import libs.exception.TokenizerException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Main {
    private static boolean isVerbose = false;

    public static boolean isVerbose() {
        return isVerbose;
    }

    public static void main(String[] args) {
        List<String> literals = Arrays.asList("show", "create", "move", "find", "file", "rename", "copy", "folder", "in",
                "to", "from", "delete", "set", "as", "get", "whereami", "take", "me", "open");
        if ((args.length >= 2) && (args[1].equals("-v"))) {
            System.out.println("=============== Running Application in verbose mode ===============");
            isVerbose = true;
        }
        if ((args.length >= 1) && (args[0].equals("cli"))) {
            launchApp(literals);
            return;
        }

        System.out.println("=============== DSL using input.txt ===============");
        try {
            Tokenizer.makeTokenizer("input.txt", literals);
        } catch (TokenizerException e) {
            e.printStackTrace();
        }

        Program p = new Program();
        try {
            p.parse();
            p.evaluate();
        } catch (FileSystemNotSupportedException | FileNotFoundException
                | UnsupportedEncodingException | ASTNodeException | TokenizerException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void launchApp(List<String> literals) {
        System.out.println("=============== DSL using CLI option ===============");
        try {
            System.out.println("Hello. You are now at: "
                    + FileSystemController.getInstance().getCurrentPath().toString());
        } catch (FileSystemNotSupportedException e) {
            System.out.println("Sorry. There is an unexpected error while starting");
            e.printStackTrace();
            System.exit(0);
        }


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
                statement = statement.concat(line + " ");
            }
        }
        System.out.println("BYE!");
    }

    private static void runProgram(String stmt, List<String> literals) {
        if (isVerbose())
            System.out.println("Running: " + stmt);
        Tokenizer.makeCliTokenizer(stmt, literals);
        Program p = new Program();

        try {
            p.parse();
            p.evaluate();
        } catch (FileSystemNotSupportedException | FileNotFoundException
                | UnsupportedEncodingException | ASTNodeException | TokenizerException e) {
            System.out.println(e.getMessage());
        }
    }
}


