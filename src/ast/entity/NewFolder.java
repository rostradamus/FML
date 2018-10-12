package ast.entity;

import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.IOException;
import java.nio.file.Path;

public class NewFolder extends NewFileSystemElement{
    String path;
    @Override
    public void parse() throws TokenizerException {
        tokenizer.getAndCheckNext("folder");
        path = tokenizer.getNext();
    }

    @Override
    public Path evaluate() throws FileSystemNotSupportedException, IOException {
        return FileSystemController.getInstance().getDirectoryPath(path);
    }
}
