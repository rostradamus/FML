package ast;

import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

public class NewFile extends NewFileSystemElement {
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
    public Path evaluate() throws IOException, FileSystemNotSupportedException {
        Path dirPath = dir.evaluate();
        Path filePath = FileSystemController.getInstance().getNewFilePath(name, dirPath);
        return filePath;
    }
}
