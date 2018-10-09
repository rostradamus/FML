package ast.action;

import ast.FileSystemElement;
import ast.Folder;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Rename extends Action {
    FileSystemElement src;
    String rename;

    @Override
    public void parse() {
        tokenizer.getAndCheckNext("rename");
        src = new FileSystemElement();
        src.parse();
        rename = tokenizer.getAndCheckNext("to");
    }

    @Override
    public Object evaluate() throws FileNotFoundException, UnsupportedEncodingException, FileSystemNotSupportedException {
        FileSystemController.getInstance().rename(src.evaluate(), rename);
        return null;
    }
}
