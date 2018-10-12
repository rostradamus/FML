package ast.action;

import ast.entity.FileSystemElement;
import ast.exception.ASTNodeException;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Rename extends Action {
    FileSystemElement src;
    String rename;

    @Override
    public void parse() throws TokenizerException, ASTNodeException {
        tokenizer.getAndCheckNext("rename");
        src = new FileSystemElement();
        src.parse();
        tokenizer.getAndCheckNext("to");
        rename = tokenizer.getNext();
    }

    @Override
    public Object evaluate() throws FileNotFoundException, UnsupportedEncodingException, FileSystemNotSupportedException {
        FileSystemController.getInstance().rename(src.evaluate(), rename);
        return null;
    }
}
