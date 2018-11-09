package ast.action;

import ast.entity.FileSystemElement;
import ast.entity.Folder;
import ast.exception.ASTNodeException;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Copy extends Action{
    FileSystemElement src;
    FileSystemElement dst;

    @Override
    public void parse() throws TokenizerException, ASTNodeException {
        tokenizer.getAndCheckNext("copy");

        src = new FileSystemElement();
        src.parse();

        tokenizer.getAndCheckNext("to");

        dst = new Folder();
        dst.parse();
    }

    @Override
    public Object evaluate() throws FileNotFoundException, UnsupportedEncodingException, FileSystemNotSupportedException {
        FileSystemController.getInstance().copy(src.evaluate(), dst.evaluate());
        return null;
    }
}
