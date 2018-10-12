package ast.entity;

import ast.exception.ASTNodeException;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

public class File extends FileSystemElement {
    String name;
    FileSystemElement dir;

    @Override
    public void parse() throws TokenizerException, ASTNodeException {
        tokenizer.getAndCheckNext("file");
        name = tokenizer.getNext();
        tokenizer.getAndCheckNext("in");
        dir = new FileSystemElement();
        dir.parse();
    }

    @Override
    public Path evaluate() throws FileNotFoundException, UnsupportedEncodingException, FileSystemNotSupportedException {
        Path dirPath = dir.evaluate();
        return FileSystemController.getInstance().getFilePath(name, dirPath);
    }
}
