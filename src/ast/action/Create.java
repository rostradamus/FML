package ast.action;

import ast.entity.FileSystemElement;
import ast.exception.ASTNodeException;
import controller.FileSystemController;
import controller.exception.FileSystemNotSupportedException;
import libs.exception.TokenizerException;

import java.io.IOException;

public class Create extends Action {
    String fileType;
    String name;
    FileSystemElement dst;

    @Override
    public void parse() throws TokenizerException, ASTNodeException {
        tokenizer.getAndCheckNext("create");

        fileType = tokenizer.getNext();

        if (!fileType.equals("file") && !fileType.equals("folder")) {
            throw new ASTNodeException("Create parse: did not run into proper file type: " + fileType);
        }

        name = tokenizer.getNext();
        tokenizer.getAndCheckNext("in");
        dst = new FileSystemElement();
        dst.parse();
    }

    @Override
    public Object evaluate() throws IOException, FileSystemNotSupportedException {

        FileSystemController.getInstance().create(dst.evaluate().resolve(name),
                fileType);
        return null;
    }
}
