package ast.action;

import ast.FileSystemElement;
import ast.Folder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Move extends Action{
    FileSystemElement src;
    FileSystemElement dst;
    @Override
    public void parse() {
        tokenizer.getAndCheckNext("move");
        src = new FileSystemElement();
        src.parse();
        tokenizer.getAndCheckNext("to");
        dst = new Folder();
        dst.parse();
    }

    @Override
    public Object evaluate() throws FileNotFoundException, UnsupportedEncodingException {
        return null;
    }
}
