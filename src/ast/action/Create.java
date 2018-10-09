package ast.action;

import ast.FileSystemElement;
import ast.Folder;
import ast.NewFileSystemElement;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Create extends Action{
    NewFileSystemElement src;
    NewFileSystemElement dst;

    @Override
    public void parse() {
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
