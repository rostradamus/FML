package controller;

import controller.exception.FileSystemNotSupportedException;
import libs.SymbolTable;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.*;
import java.util.List;
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

    public Path getNewFilePath(String newFileName, Path dirPath) throws FileSystemNotSupportedException, FileAlreadyExistsException, IOException {
        Path fileToCreatePath = dirPath.resolve(newFileName);
        System.out.println("File to Create: " + newFileName);
        return fileToCreatePath;
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


    public boolean create(Path src) throws FileAlreadyExistsException{
        if (Files.exists(src)) {
            System.out.println("File not created because some file with the same name already exists!");
            return false;
        }
        try {
            Files.createFile(src);
            System.out.println(src.getFileName() + " created to " + src.getParent().toString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(Path src) {
        try {
            Files.deleteIfExists(src);
            updateSymbolTable(src, null);
            System.out.println("File "+ src.getFileName().toString() + " deleted from " + src.getParent().toString());
            return true;
        } catch (DirectoryNotEmptyException e){
            System.out.println("Directory " + src.toString() + "is not empty");
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean find(String name) throws FileSystemNotSupportedException {
        return find(name, currPath);
    }

    public boolean find(String name, String path) throws FileSystemNotSupportedException {
        Path start = Paths.get(path);
        boolean isPathFile = start.toFile().isFile();
        if (isPathFile) {
            findFiles(name, start).forEach(System.out::println);
        } else {
            find(name);
        }

        return false;
    }

    public boolean move(Path src, Path dst) {
        try {
            Path extendedDst = this.extendDirectoryPath(src, dst);
            Files.move(src, extendedDst);
            System.out.println("moved " + src.toString() + " to " + extendedDst.toString());
            updateSymbolTable(src, extendedDst);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean copy(Path src, Path dest)  {
        Path copiedPath = dest.resolve(src.getFileName());
        try {
            Files.copy(src, copiedPath);
            System.out.println("File " + src.getFileName().toString() + " copied to " + copiedPath.getParent().toString());
            updateSymbolTable(src, copiedPath);
            return true;
        } catch (FileAlreadyExistsException e) {
            System.out.println("File " + src.getFileName().toString() + " already exists in " + copiedPath.getParent().toString());
        }  catch (IOException e ){
            e.printStackTrace();
        }
        return false;
    }

    public boolean rename(Path src, String rename) {
        File file = new File(src.toString());
        String newPath = src.getParent() + "/" + rename;
        File renamedFile = new File(newPath);
        updateSymbolTable(src, Paths.get(newPath));
        System.out.println("File " + src.getFileName().toString() + " renamed to " +renamedFile.getPath());

        return file.renameTo(renamedFile);

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

    private Path extendDirectoryPath(Path src, Path dst) {
        String[] srcPath = src.toString().split("/");
        Path pp = Paths.get(dst.toString(), srcPath[srcPath.length - 1]);
        return pp;
    }


    /**
     *
     * @param src
     * @param newPath if it is null it will delete src mapping from symbol table.
     */
    private void updateSymbolTable(Path src, Path newPath) {
        if (newPath == null) {
            SymbolTable.getInstance();
            SymbolTable.getInstance().getTable().entrySet().removeIf(entry -> entry.getValue().equals(src));
            SymbolTable.getInstance();
        }

        SymbolTable.getInstance().getTable().forEach(((key, value) -> {
            if (value.equals(src)) {
                SymbolTable.getInstance().getTable().put(key, newPath);
            }
        }));
    }
}
