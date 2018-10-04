package libs;


import controller.exception.FileSystemNotSupportedException;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public abstract class Node {
    protected Tokenizer tokenizer = Tokenizer.getTokenizer();
    static protected PrintWriter writer;

    abstract public void parse();
    abstract public Object evaluate() throws FileNotFoundException, UnsupportedEncodingException, FileSystemNotSupportedException;


}
