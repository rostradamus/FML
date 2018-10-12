package libs;


import ast.exception.ASTNodeException;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public abstract class Node {
    protected Tokenizer tokenizer = Tokenizer.getTokenizer();
    static protected PrintWriter writer;

    abstract public void parse() throws ASTNodeException, TokenizerException;
    abstract public Object evaluate() throws IOException, FileSystemNotSupportedException;


}
