package controller;

import controller.exception.FileSystemNotSupportedException;

public class ControllerPOCRunner {
    public void run() {
        FileSystemController fileSystemController = FileSystemController.getInstance();
        try {
            String home = System.getProperty("user.home");

            printDescription("show", true);
            fileSystemController.show("hello.txt");

            // USE BELOW function for absolute path
            printDescription("show", false);
            fileSystemController.show("hello.txt", home);

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
