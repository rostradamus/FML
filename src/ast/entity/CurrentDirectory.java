package ast.entity;

import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

public class CurrentDirectory extends FileSystemElement {
    @Override
    public void parse() throws TokenizerException {
        tokenizer.getAndCheckNext("me");
    }

    @Override
    public Path evaluate() throws FileSystemNotSupportedException {
        return FileSystemController.getInstance().getCurrentPath();
    }

}
