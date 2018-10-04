package ast.action;

import ast.FileSystemElement;
import ast.Folder;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Move extends Action{
    Object src;
    Object dst;
    @Override
    public void parse() {
        tokenizer.getAndCheckNext("move");
        src = new FileSystemElement();
        dst = new Folder();
    }

    @Override
    public Object evaluate() throws FileNotFoundException, UnsupportedEncodingException {
        return null;
    }
}
