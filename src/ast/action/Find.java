package ast.action;

import ast.entity.FileSystemElement;
import ast.exception.ASTNodeException;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Find extends Action{
    String target;
    FileSystemElement from;

    @Override
    public void parse() throws TokenizerException, ASTNodeException {
        tokenizer.getAndCheckNext("find");
        target = tokenizer.getNext();
        tokenizer.getAndCheckNext("from");
        from = new FileSystemElement();
        from.parse();
    }

    @Override
    public Object evaluate() throws FileNotFoundException, UnsupportedEncodingException, FileSystemNotSupportedException {
        FileSystemController.getInstance().find(target, from.evaluate());
        return null;
    }
}
