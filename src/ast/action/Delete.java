package ast.action;

import ast.entity.FileSystemElement;
import ast.exception.ASTNodeException;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Delete extends Action{
    FileSystemElement src;
    @Override
    public void parse() throws TokenizerException, ASTNodeException {
        tokenizer.getAndCheckNext("delete");
        src = new FileSystemElement();
        src.parse();
    }

    @Override
    public Object evaluate() throws FileNotFoundException, UnsupportedEncodingException, FileSystemNotSupportedException {
        FileSystemController.getInstance().delete(src.evaluate());
        return null;

    }
}
