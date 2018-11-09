package ast;

import ast.action.*;
import ast.entity.Alias;
import ast.entity.CurrentDirectory;
import ast.entity.File;
import ast.entity.Folder;
import ast.exception.ASTNodeException;
import controller.exception.FileSystemNotSupportedException;
import libs.Node;
import libs.exception.TokenizerException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Program extends Node {
    private List<Statement> statements = new ArrayList<>();

    @Override
    public void parse() throws ASTNodeException, TokenizerException {
        while (tokenizer.moreTokens()) {
            Statement s = null;
            if (tokenizer.checkToken("show")) {
                s = new Show();
            } else if (tokenizer.checkToken("find")) {
                s = new Find();
            } else if (tokenizer.checkToken("file")) {
                s = new File();
            } else if (tokenizer.checkToken("folder")) {
                s = new Folder();
            } else if (tokenizer.checkToken("move")){
                s = new Move();
            } else if (tokenizer.checkToken("copy")) {
                s = new Copy();
            } else if (tokenizer.checkToken("delete")){
                s = new Delete();
            } else if (tokenizer.checkToken("create")) {
                s = new Create();
            } else if (tokenizer.checkToken("rename")) {
                s = new Rename();
            } else if (tokenizer.checkToken("set")) {
                s = new Set();
            } else if (tokenizer.checkToken("get")) {
                s = new Alias();
            } else if (tokenizer.checkToken("take")){
                s = new ChangeDirectory();
            } else if (tokenizer.checkToken("whereami")) {
                s = new WhereAmI();
            } else if (tokenizer.checkToken("open")) {
                s = new Open();
            } else {
                throw new ASTNodeException("Program parse: did not run into given literals");
            }
            s.parse();

            statements.add(s);
        }
    }

    @Override
    public Object evaluate() throws FileSystemNotSupportedException, FileNotFoundException, UnsupportedEncodingException {
        for (Statement s : statements) {
            try {
                s.evaluate();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
