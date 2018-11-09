package ast.entity;

import ast.Statement;
import ast.exception.ASTNodeException;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

public class FileSystemElement extends Statement {
    FileSystemElement element;
    @Override
    public void parse() throws TokenizerException, ASTNodeException {
        if (tokenizer.checkToken("file")) {
            element = new File();
        } else if (tokenizer.checkToken("folder")) {
            element = new Folder();
        } else if (tokenizer.checkToken("get")) {
            element = new Alias();
        } else if(tokenizer.checkToken("me")) {
            element = new CurrentDirectory();
        } else {
            throw new ASTNodeException("FileElement parse: did not run into given literals");
        }
        element.parse();
    }

    @Override
    public Path evaluate() throws FileNotFoundException, UnsupportedEncodingException, FileSystemNotSupportedException {
        return element.evaluate();
    }
}