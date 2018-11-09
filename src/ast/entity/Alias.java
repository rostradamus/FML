package ast.entity;

import controller.exception.FileSystemNotSupportedException;
import libs.SymbolTable;
import libs.exception.TokenizerException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

public class Alias extends FileSystemElement {
    String varName;
    @Override
    public void parse() throws TokenizerException {
        tokenizer.getAndCheckNext("get");
        varName = tokenizer.getNext();
    }

    @Override
    public Path evaluate() throws FileNotFoundException, UnsupportedEncodingException, FileSystemNotSupportedException {
        return (Path) SymbolTable.getInstance().get(varName);
    }
}
