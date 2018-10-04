package ast;

import controller.exception.FileSystemNotSupportedException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

public class FileSystemElement extends Statement{
    FileSystemElement element;
    @Override
    public void parse() {
        if (tokenizer.checkToken("file")) {
            element = new File();
        } else if (tokenizer.checkToken("folder")) {
            element = new Folder();
        }
        element.parse();
    }

    @Override
    public Path evaluate() throws FileNotFoundException, UnsupportedEncodingException, FileSystemNotSupportedException {
        return element.evaluate();
    }
}
