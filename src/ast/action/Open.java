package ast.action;

import ast.entity.FileSystemElement;
import ast.exception.ASTNodeException;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.IOException;

public class Open extends Action {
    FileSystemElement src;

    @Override
    public void parse() throws ASTNodeException, TokenizerException {
        tokenizer.getAndCheckNext("open");

        src = new FileSystemElement();
        src.parse();
    }

    @Override
    public Object evaluate() throws IOException, FileSystemNotSupportedException {
        FileSystemController.getInstance().open(src.evaluate());
        return null;
    }
}
