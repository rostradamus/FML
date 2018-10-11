package ast;

import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.List;

public class Folder extends FileSystemElement{
    String path;
    @Override
    public void parse() {
        tokenizer.getAndCheckNext("folder");
        path = tokenizer.getNext();
    }



    @Override
    public Path evaluate() throws FileSystemNotSupportedException, FileNotFoundException, UnsupportedEncodingException {
        return FileSystemController.getInstance().getDirectoryPath(path);
    }
}
