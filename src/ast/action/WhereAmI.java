package ast.action;

import ast.entity.FileSystemElement;
import ast.exception.ASTNodeException;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.IOException;

public class WhereAmI extends Action {
    @Override
    public void parse() throws ASTNodeException, TokenizerException {
        tokenizer.getAndCheckNext("whereami");
    }

    @Override
    public Object evaluate() throws FileSystemNotSupportedException {
        System.out.println("You are at: " + FileSystemController.getInstance().getCurrentPath().toString());
        return null;
    }
}
