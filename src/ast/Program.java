package ast;

import ast.action.*;
import controller.exception.FileSystemNotSupportedException;
import libs.Node;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Program extends Node {
    private List<Statement> statements = new ArrayList<>();

    @Override
    public void parse() {
        while (tokenizer.moreTokens()) {
            Statement s = null;
            if (tokenizer.checkToken("show")) {
                s = new Show();
            } else if (tokenizer.checkToken("find")) {
                s = new Find();
            } else if (tokenizer.checkToken("file")) {
                s = new File();
            } else {
                System.out.println("Fuck !");
            }
            s.parse();
            statements.add(s);
        }
    }

    @Override
    public Object evaluate() throws FileSystemNotSupportedException, FileNotFoundException, UnsupportedEncodingException {
        for (Statement s : statements) {
            s.evaluate();
        }
        return null;
    }
}