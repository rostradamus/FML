package ui;

import ast.Program;
import controller.ControllerPOCRunner;
import controller.exception.FileSystemNotSupportedException;
import libs.Tokenizer;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        // Controller POC runner
//        ControllerPOCRunner cpr = new ControllerPOCRunner();
//        cpr.run();

        System.out.println("=============== DSL POC ===============");
        List<String> literals = Arrays.asList("show", "find", "called");
        Tokenizer.makeTokenizer("input.txt", literals);
        Program p = new Program();
        p.parse();
        try {
            p.evaluate();
        } catch (FileSystemNotSupportedException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }

    }
}
