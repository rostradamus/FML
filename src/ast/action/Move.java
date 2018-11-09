package ast.action;

import ast.entity.FileSystemElement;
import ast.exception.ASTNodeException;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Move extends Action{
    FileSystemElement src;
    FileSystemElement dst;
    @Override
    public void parse() throws TokenizerException, ASTNodeException {
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
