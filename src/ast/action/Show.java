package ast.action;

import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Show extends Action {
    String kind;
    String name;
    @Override
    public void parse() {
        tokenizer.getAndCheckNext("show");
        kind = tokenizer.getNext();
        tokenizer.getAndCheckNext("called");
        name = tokenizer.getNext().trim();
    }

    @Override
    public void evaluate() throws FileSystemNotSupportedException, FileNotFoundException, UnsupportedEncodingException {
        FileSystemController.getInstance().show(name);
    }
}
