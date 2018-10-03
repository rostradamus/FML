package ast;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class Folder extends FileSystemElement{
    List<FileSystemElement> fsElements;


    @Override
    public void parse() {

    }

    @Override
    public void evaluate() throws FileNotFoundException, UnsupportedEncodingException {
    }
}
