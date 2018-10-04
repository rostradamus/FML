package controller;

import controller.exception.FileSystemNotSupportedException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ControllerPOCRunner {
    public void run() {
        FileSystemController fileSystemController = FileSystemController.getInstance();
        try {
            String home = System.getProperty("user.home");
            Path src;
            printDescription("show", true);
            src = Paths.get(home,"hello.txt");
            fileSystemController.show(src);

            printDescription("find", true);
            fileSystemController.find("random");

            printDescription("find", false);
            fileSystemController.find("random", home + "/" + "temp");
        } catch (FileSystemNotSupportedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printDescription(String method, boolean isCurrentPath) {
        String pathDescription = " (with " + (isCurrentPath ? "current" : "absolute") + " path)";
        System.out.println("================== " + method + pathDescription + " ==================");
    }
}
