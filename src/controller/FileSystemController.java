package controller;

import controller.exception.FileSystemNotSupportedException;
import libs.SymbolTable;
import ui.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.DecimalFormat;
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
        this.depthOption = DEFAULT_DEPTH_OPTION;
    }

    public static FileSystemController getInstance() {
        if (instance == null)
            instance = new FileSystemController();
        return instance;
    }

    public Path getCurrentPath() throws FileSystemNotSupportedException {
        return getDirectoryPath(currPath);
    }

    public void setCurrPath(Path currPath) {
        this.currPath = currPath.toString();
    }

    public Path getFilePath(String name, Path dirPath) throws FileSystemNotSupportedException {
        Path filePath = Paths.get(dirPath.toString(), name);
        if (!filePath.toFile().isFile())
            throw new FileSystemNotSupportedException(name + " in " + dirPath.toString() + " is not a file");
        if (Main.isVerbose())
            System.out.println("Found a file: " + name + ", from " + dirPath.toString());
        return filePath;
    }

    public Path getNewFilePath(String newFileName, Path dirPath) throws FileSystemNotSupportedException, FileAlreadyExistsException, IOException {
        Path fileToCreatePath = dirPath.resolve(newFileName);
        System.out.println("File to Create: " + newFileName);
        return fileToCreatePath;
    }

    private Path handleAndGetSpecialPath(String path) throws FileSystemNotSupportedException {

        List<String> pathElems = Arrays.asList(path.split("/"));
        Stack<String> currPathElems = new Stack<>();
        currPathElems.addAll(Arrays.asList(currPath.split("/")));

        for(String elem : pathElems) {
            if (elem.equals(".")) {
                continue;
            } else if (elem.equals("..")) {
                currPathElems.pop();
            } else if (elem.startsWith("...")) {
                throw new FileSystemNotSupportedException("Can't resolve path: " + path);
            } else {
                currPathElems.push(elem);
            }
        }
        String resolvedPath = String.join("/", new ArrayList<String>(currPathElems));
        return Paths.get(resolvedPath);
    }

    public Path getDirectoryPath(String path) throws FileSystemNotSupportedException {
        Path dirPath;
        if (!path.startsWith("/")) {
            dirPath = handleAndGetSpecialPath(path);
        } else {
            dirPath = Paths.get(path);
        }

        if (!dirPath.toFile().isDirectory())
            throw new FileSystemNotSupportedException(path + " is not a directory.");
        if (Main.isVerbose())
            System.out.println("Found a folder: " + path);
        return dirPath;
    }

    public void setDepthOption(int customDepth) throws FileSystemNotSupportedException {
        if (customDepth > 10)
            throw new FileSystemNotSupportedException("File system service do not support depth deeper than 10");
        this.depthOption = customDepth;
    }

    public boolean create(Path target, String fileType) {
        if (Files.exists(target)) {
            System.out.println("File not created because some file with the same name already exists!");
            return false;
        }
        try {
            Path result = fileType.equals("file") ? Files.createFile(target) : Files.createDirectory(target);
            System.out.println(fileType + ": " + target.getFileName() + " created to " + target.getParent());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(Path src) {
        try {
            String output = Files.isDirectory(src) ? "Folder " : "File ";
            deleteHelper(src.toFile());
            System.out.println(output + src.getFileName().toString() + " deleted from " + src.getParent().toString());
            return true;
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteHelper(File file) throws IOException{
        if(!file.isDirectory()){
            updateSymbolTable(file.toPath(), null);
            return file.delete();
          }
            for (File f : file.listFiles()) {
                deleteHelper(f);
            }
            updateSymbolTable(file.toPath(), null);
            return file.delete();
    }

    public boolean find(String name, Path from) throws FileSystemNotSupportedException {
        System.out.println("=============== Find files/dirs with name containing: " + name + " ===============");
        findFiles(name, from)
                .map(Path::toFile)
                .sorted((File file1, File file2) -> {
                    if ((file1.isDirectory() && file2.isDirectory())
                            || (file1.isFile() && file2.isFile())) {
                        return file1.getPath().compareTo(file2.toString());
                    } else {
                        return file1.isDirectory() ? -1 : 1;
                    }
                })
                .forEach((File file) -> printFileInfoLine(file, false));
        System.out.println("=============== End of List ===============");
        return true;
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

    public boolean copy(Path src, Path dest){
        String output = Files.isDirectory(src) ? "Folder " : "File ";
        try {
            Path newFolder = dest.resolve(src.getFileName());
            Files.copy(src, newFolder);
            Files.walk(src).forEach( s -> {
                try {
                    Path d = newFolder.resolve(src.relativize(s));
                    if(Files.exists(d)) return;
                    Files.copy(s, d);
                } catch( Exception e ) {
                    e.printStackTrace();
                }
            });
            System.out.println(output + src.getFileName().toString() + " copied to " + dest.toString());
        } catch(FileAlreadyExistsException ex ) {
            System.out.println(output + src.getFileName().toString() + " already exists in " + dest.toString());
        } catch (IOException e){

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
            printFileContent(path);
        } else {
            printFileList(path);
        }
    }

    private void printFileContent(Path src) throws FileSystemNotSupportedException {
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

    private void printFileList(Path src) {
        System.out.println("=============== Show file list for directory: " + src.toString() + " ===============");
        File[] files = src.toFile().listFiles();
        if (files == null) {
            System.out.println("No file found in the directory");
            return;
        }
        System.out.println("FileType             Size                  name");
        Stream.of(files)
                .sorted((File file1, File file2) -> {
                    if ((file1.isDirectory() && file2.isDirectory())
                        || (file1.isFile() && file2.isFile())) {
                        return file1.getPath().compareTo(file2.toString());
                    } else {
                        return file1.isDirectory() ? -1 : 1;
                    }
                })
                .forEach((File file) -> printFileInfoLine(file, true));
        System.out.println("=============== End of list ===============");
    }

    private Stream<Path> findFiles(String name, Path start) throws FileSystemNotSupportedException {
        Stream<Path> stream;
        try {
            stream = Files.find(start,depthOption, ((path, basicFileAttributes) -> {
                File file = path.toFile();
                return file.getName().contains(name);
            }));
        } catch (IOException e) {
            throw new FileSystemNotSupportedException(e.getMessage());
        }
        return stream;
    }

    private Path extendDirectoryPath(Path src, Path dst) {
        String[] srcPath = src.toString().split("/");
        return Paths.get(dst.toString(), srcPath[srcPath.length - 1]);
    }

    private void printFileInfoLine(File file, boolean isOnlyFileName) {
        String fileType = file.isFile() ? " <File> " : "<Folder>";
        int length = 15;
        StringBuilder sb = new StringBuilder(length);
        DecimalFormat df = new DecimalFormat("#.##");
        String fileSize = Double.valueOf(df.format((double) file.length() / 1024)) + " kb";
        int padding = file.isFile() ? length - fileSize.length() : length;
        for (int i = 1; i < padding; i++) {
            sb.append(" ");
        }
        if (file.isFile())
            sb.append(fileSize);
        String filePath = isOnlyFileName ?  file.getName() : file.getPath();
        System.out.println(fileType + "   " + sb.toString() + "         " + filePath);
    }

    /**
     *
     * @param src
     * @param newPath if it is null it will delete src mapping from symbol table.
     */
    private void updateSymbolTable(Path src, Path newPath) {
        if (newPath == null) {
            SymbolTable.getInstance().getTable().entrySet().removeIf(entry -> entry.getValue().equals(src));
        }

        SymbolTable.getInstance().getTable().forEach(((key, value) -> {
            if (value.equals(src)) {
                SymbolTable.getInstance().getTable().put(key, newPath);
            }
        }));
    }
}
