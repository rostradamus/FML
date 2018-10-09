package ast;

import controller.exception.FileSystemNotSupportedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;


public class NewFileSystemElement extends Statement{
    NewFileSystemElement element;
    @Override
    public void parse() {
        if (tokenizer.checkToken("file")) {
            element = new NewFile();
        } else if (tokenizer.checkToken("folder")) {
            element = new NewFolder();
        }
        element.parse();
    }

    @Override
    public Path evaluate() throws UnsupportedEncodingException, FileSystemNotSupportedException, IOException {
        return element.evaluate();
    }
}
