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

    public Path getFilePath(String name, Path dirPath) throws FileSystemNotSupportedException {
        Path filePath = Paths.get(dirPath.toString(), name);
        if (!filePath.toFile().isFile())
            throw new FileSystemNotSupportedException(name + " in " + dirPath.toString() + " is not a file");
        System.out.println("Found a file: " + name + ", from " + dirPath.toString());
        return filePath;
    }

    public Path getDirectoryPath(String path) throws FileSystemNotSupportedException {

        Path dirPath = Paths.get(path);
        if (!dirPath.toFile().isDirectory())
            throw new FileSystemNotSupportedException(path + " is not a directory.");
        System.out.println("Found a folder: " + path);
        return dirPath;
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
    public void show(Path path) throws FileSystemNotSupportedException {
        boolean isPathFile = path.toFile().isFile();
        if (isPathFile) {
            getFileContent(path);
        } else {
            getFileList(path);
        }
    }

    private void getFileContent(Path src) throws FileSystemNotSupportedException {
        System.out.println("=============== Show file content for " + src.toString() + " ===============");
        List<String> lines;
        try {
            lines = Files.readAllLines(src);
        } catch (IOException e) {
            throw new FileSystemNotSupportedException(e.getMessage());
        }
        lines.forEach(System.out::println);
        System.out.println("=============== End of file content for ===============");
    }

    private void getFileList(Path src) throws FileSystemNotSupportedException {
        System.out.println("=============== Show file list for " + src.toString() + " ===============");
        File[] files = src.toFile().listFiles();
        Arrays.asList(files).forEach((file -> {
            System.out.println(file.getName());
        }));
        System.out.println("=============== End of file list for ===============");
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