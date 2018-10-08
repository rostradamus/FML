package ast.action;

import ast.File;
import ast.FileSystemElement;
import ast.Folder;
import ast.Statement;
import ast.exception.ASTNodeException;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Show extends Action {
    FileSystemElement src;
    @Override
    public void parse() throws ASTNodeException {
        tokenizer.getAndCheckNext("show");
        src = new FileSystemElement();
        src.parse();
    }

    @Override
    public Object evaluate() throws FileSystemNotSupportedException, FileNotFoundException, UnsupportedEncodingException {
        FileSystemController.getInstance().show(src.evaluate());
        return null;
    }
}
