package ast.action;

import ast.FileSystemElement;
import controller.exception.FileSystemNotSupportedException;
import libs.SymbolTable;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class Set extends Action{
    private String name;
    private FileSystemElement element;
    @Override
    public void parse() {
        tokenizer.getAndCheckNext("set");
        name = tokenizer.getAndCheckUnreservedNext();
        tokenizer.getAndCheckNext("as");
        element = new FileSystemElement();
        element.parse();
        System.out.println(name);
    }

    @Override
    public Object evaluate() throws FileNotFoundException, UnsupportedEncodingException, FileSystemNotSupportedException {
//        SymbolTable.getInstance().put(name, element.evaluate());
        SymbolTable symbolTable = SymbolTable.getInstance();
        symbolTable.put(name, element.evaluate());
        return null;
    }
}
