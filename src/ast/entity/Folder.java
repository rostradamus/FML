package ast.entity;

import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

public class Folder extends FileSystemElement{
    String path;
    @Override
    public void parse() throws TokenizerException {
        tokenizer.getAndCheckNext("folder");
        path = tokenizer.getNext();
    }



    @Override
    public Path evaluate() throws FileSystemNotSupportedException, FileNotFoundException, UnsupportedEncodingException {
        return FileSystemController.getInstance().getDirectoryPath(path);
    }
}
