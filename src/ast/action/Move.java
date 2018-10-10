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
        if (tokenizer.checkToken("get")) {
            tokenizer.getNext();
            String name = tokenizer.getNext();
            src = (FileSystemElement) SymbolTable.getInstance().get(name);
        } else {
            src = new FileSystemElement();
            src.parse();

        }

        tokenizer.getAndCheckNext("to");

        if (tokenizer.checkToken("get")) {
            tokenizer.getNext();
            String name = tokenizer.getNext();
            dst = (FileSystemElement) SymbolTable.getInstance().get(name);
        } else {
            dst = new Folder();
            dst.parse();
        }
    }

    @Override
    public Object evaluate() throws FileSystemNotSupportedException, FileNotFoundException, UnsupportedEncodingException {
        FileSystemController.getInstance().move(src.evaluate(), dst.evaluate());
        return null;
    }
}
