package ast.action;

import ast.FileSystemElement;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.SymbolTable;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Find extends Action{
    FileSystemElement src;

    @Override
    public void parse() {
        tokenizer.getAndCheckNext("find");
        if (tokenizer.checkToken("get")) {
            tokenizer.getNext();
            String name = tokenizer.getNext();
            src = (FileSystemElement) SymbolTable.getInstance().get(name);
        } else {
            src = new FileSystemElement();
            src.parse();
        }
    }

    @Override
    public Object evaluate() throws FileNotFoundException, UnsupportedEncodingException, FileSystemNotSupportedException {
        FileSystemController.getInstance().find(src.evaluate().getFileName().toString(), src.evaluate().toString());
        return null;
    }
}
