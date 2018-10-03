package controller;

public class ControllerPOCRunner {
    public void run() {
        FileSystemController fileSystemController = FileSystemController.getInstance();
        fileSystemController.show("hello.txt");
        // USE BELOW function for absolute path
        fileSystemController.show("hello.txt", "/Users/rolee");
    }
}
