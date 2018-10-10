package ast.action;

import ast.FileSystemElement;
import ast.Folder;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.SymbolTable;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Copy extends Action{
    FileSystemElement src;
    FileSystemElement dst;

    @Override
    public void parse() {
        tokenizer.getAndCheckNext("copy");
        if (tokenizer.checkToken("get")) {
            tokenizer.getNext();
            String name = tokenizer.getNext();
            src = (FileSystemElement) SymbolTable.getInstance().get(name);
        } else {
            src = new FileSystemElement();
            src.parse();
            tokenizer.getAndCheckNext("to");

        }

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
    public Object evaluate() throws FileNotFoundException, UnsupportedEncodingException, FileSystemNotSupportedException {
        FileSystemController.getInstance().copy(src.evaluate(), dst.evaluate());
        return null;
    }
}
