package ast.action;

import ast.entity.FileSystemElement;
import ast.exception.ASTNodeException;
import controller.exception.FileSystemNotSupportedException;
import libs.SymbolTable;
import libs.exception.TokenizerException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Set extends Action{
    private String name;
    private FileSystemElement element;
    @Override
    public void parse() throws TokenizerException, ASTNodeException {
        tokenizer.getAndCheckNext("set");
        name = tokenizer.getAndCheckUnreservedNext();

        tokenizer.getAndCheckNext("as");
        element = new FileSystemElement();
        element.parse();
        System.out.println(name);
    }

    @Override
    public Object evaluate() throws FileNotFoundException, UnsupportedEncodingException, FileSystemNotSupportedException {
        SymbolTable symbolTable = SymbolTable.getInstance();
        symbolTable.put(name, element.evaluate());
        return null;
    }
}
