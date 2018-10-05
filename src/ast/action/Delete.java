package ast.action;

import ast.FileSystemElement;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Delete extends Action{
    FileSystemElement src;
    @Override
    public void parse() {
        tokenizer.getAndCheckNext("delete");
        src = new FileSystemElement();
        src.parse();

    }

    @Override
    public Object evaluate() throws FileNotFoundException, UnsupportedEncodingException {
        return null;

    }
}
