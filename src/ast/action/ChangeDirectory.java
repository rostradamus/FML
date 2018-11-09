package ast.action;

import ast.entity.CurrentDirectory;
import ast.entity.Folder;
import ast.exception.ASTNodeException;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.IOException;
import java.nio.file.Path;

public class ChangeDirectory extends Action {
    CurrentDirectory currentDirectory;
    String dst;

    @Override
    public void parse() throws ASTNodeException, TokenizerException {
        tokenizer.getAndCheckNext("take");
        currentDirectory = new CurrentDirectory();
        currentDirectory.parse();
        tokenizer.getAndCheckNext("to");
        dst = tokenizer.getNext();
    }

    @Override
    public Object evaluate() throws IOException, FileSystemNotSupportedException {
        Path prevDirPath = currentDirectory.evaluate();
        Path currDirPath = FileSystemController.getInstance().getDirectoryPath(dst);
        FileSystemController.getInstance().setCurrPath(currDirPath);
        System.out.println("Moved current directory from " + prevDirPath.toString()
            + " to " + currDirPath.toString());
        return null;
    }
}
