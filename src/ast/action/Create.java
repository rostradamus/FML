package ast.action;

import ast.entity.NewFileSystemElement;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.IOException;

public class Create extends Action{
    NewFileSystemElement src;
    NewFileSystemElement dst;

    @Override
    public void parse() throws TokenizerException {
        tokenizer.getAndCheckNext("create");
        src = new NewFileSystemElement();
        src.parse();
    }

    @Override
    public Object evaluate() throws IOException, FileSystemNotSupportedException {
        FileSystemController.getInstance().create(src.evaluate());
        return null;
    }
}
