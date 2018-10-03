package controller.exception;

public class FileSystemNotSupportedException extends Exception {
    public FileSystemNotSupportedException() {
        super();
    }

    public FileSystemNotSupportedException(String msg) {
        super(msg);
    }
}
