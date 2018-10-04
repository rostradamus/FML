package ast;

import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

public class File extends FileSystemElement {
    String name;
    Folder dir;

    @Override
    public void parse() {
        tokenizer.getAndCheckNext("file");
        name = tokenizer.getNext();
        tokenizer.getAndCheckNext("in");
        dir = new Folder();
        dir.parse();
    }

    @Override
    public Path evaluate() throws FileNotFoundException, UnsupportedEncodingException, FileSystemNotSupportedException {
        Path dirPath = dir.evaluate();
        Path filePath = FileSystemController.getInstance().getFilePath(name, dirPath);
        return filePath;
    }
}
