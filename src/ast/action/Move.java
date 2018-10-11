package ast.action;

import ast.FileSystemElement;
import ast.Folder;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.SymbolTable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

public class Move extends Action{
    FileSystemElement src;
    FileSystemElement dst;
    @Override
    public void parse() {
        tokenizer.getAndCheckNext("move");
        src = new FileSystemElement();
        src.parse();

        tokenizer.getAndCheckNext("to");

        dst = new FileSystemElement();
        dst.parse();
    }

    @Override
    public Object evaluate() throws FileSystemNotSupportedException, FileNotFoundException, UnsupportedEncodingException {
        FileSystemController.getInstance().move(src.evaluate(), dst.evaluate());
        return null;
    }
}
