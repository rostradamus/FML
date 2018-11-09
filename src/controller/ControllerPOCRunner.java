package controller;

import controller.exception.FileSystemNotSupportedException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ControllerPOCRunner {
    public static void main(String[] args) {
        (new ControllerPOCRunner()).run();
    }

    private void run() {
        FileSystemController fileSystemController = FileSystemController.getInstance();
        try {
            String home = System.getProperty("user.home");
            Path src;
            printDescription("show", true);
            src = Paths.get(home,"hello.txt");
            fileSystemController.show(src);

        } catch (FileSystemNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printDescription(String method, boolean isCurrentPath) {
        String pathDescription = " (with " + (isCurrentPath ? "current" : "absolute") + " path)";
        System.out.println("================== " + method + pathDescription + " ==================");
    }
}
