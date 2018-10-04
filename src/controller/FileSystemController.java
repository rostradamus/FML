package controller;

import controller.exception.FileSystemNotSupportedException;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class FileSystemController {
    private static FileSystemController instance;
    private String currPath;
    private int depthOption;
    private static final int DEFAULT_DEPTH_OPTION = 5;

    private FileSystemController() {
        this.currPath = System.getProperty("user.home");
        this.depthOption = 5;
    }

    public static FileSystemController getInstance() {
        if (instance == null)
            instance = new FileSystemController();
        return instance;
    }

    public void setDepthOption(int customDepth) throws FileSystemNotSupportedException {
        if (customDepth > 10)
            throw new FileSystemNotSupportedException("File system service do not support depth deeper than 10");
        this.depthOption = customDepth;
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

    public boolean find(String name) throws FileSystemNotSupportedException {
        return find(name, currPath);
    }

    public boolean find(String name, String path) throws FileSystemNotSupportedException {
        Path start = Paths.get(path);
        findFiles(name, start).forEach(System.out::println);

        return false;
    }

    public boolean move() {
        return false;
    }

    public boolean rename() {
        return false;
    }

    // TODO: Below methods are POC codes
    public boolean show(String name) throws FileSystemNotSupportedException {
        return show(name, currPath);
    }

    public boolean show(String name, String path) throws FileSystemNotSupportedException {
        Path src = Paths.get(path + "/" + name);
        System.out.println(src.toString());
        getFileContent(src).forEach(System.out::println);

        return false;
    }

    private List<String> getFileContent(Path src) throws FileSystemNotSupportedException {
        List<String> lines;
        try {
            lines = Files.readAllLines(src);
        } catch (IOException e) {
            throw new FileSystemNotSupportedException(e.getMessage());
        }
        return lines;
    }

    private Stream<Path> findFiles(String name, Path start) throws FileSystemNotSupportedException {
        Stream<Path> stream;
        try {
            stream = Files.find(start,5, ((path, basicFileAttributes) -> {
                File file = path.toFile();
                return !file.isDirectory() && file.getName().contains(name);
            }));
        } catch (IOException e) {
            throw new FileSystemNotSupportedException(e.getMessage());
        }
        return stream;
    }
}
