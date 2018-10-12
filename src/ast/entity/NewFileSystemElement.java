package ast.entity;

import ast.Statement;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;


public class NewFileSystemElement extends Statement {
    NewFileSystemElement element;
    @Override
    public void parse() throws TokenizerException {
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
