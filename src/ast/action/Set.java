package ast.action;

import ast.FileSystemElement;
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
        name = tokenizer.getNext();
        tokenizer.getAndCheckNext("as");
        element = new FileSystemElement();
        element.parse();
        System.out.println(name);
    }

    @Override
    public Object evaluate() throws FileNotFoundException, UnsupportedEncodingException {
        SymbolTable.getInstance().put(name, element);
        return null;
    }
}
