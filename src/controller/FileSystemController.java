package controller;

import controller.exception.FileSystemNotSupportedException;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class FileSystemController {
    private static FileSystemController instance;
    private String currPath;

    private FileSystemController() {
        this.currPath = System.getProperty("user.home");
    }

    public static FileSystemController getInstance() {
        if (instance == null)
            instance = new FileSystemController();
        return instance;
    }

    public boolean copy() {
        return false;
    }

    public boolean create() {
        return false;
    }

    public boolean delete() {
        return false;
    }

    public boolean find() {
        return false;
    }

    public boolean move() {
        return false;
    }

    public boolean rename() {
        return false;
    }

    // TODO: Below methods are POC codes
    public boolean show(String name, String path) {
        Path src = Paths.get(path + "/" +name);
        try {
            getFileContent(src).forEach(System.out::println);
        } catch (FileSystemNotSupportedException e) {
            System.out.println("Hmm something is wrong");
        }

        return false;
    }

    public boolean show(String name) {
        Path src = Paths.get(currPath + "/" + name);
        try {
            getFileContent(src).forEach(System.out::println);
        } catch (FileSystemNotSupportedException e) {
            System.out.println("Hmm something is wrong");
        }

        return false;
    }

    private List<String> getFileContent(Path src) throws FileSystemNotSupportedException {
        List<String> lines;
        try {
            lines = Files.readAllLines(src);
        } catch (IOException e) {
            throw new FileSystemNotSupportedException();
        }
        return lines;
    }
}
