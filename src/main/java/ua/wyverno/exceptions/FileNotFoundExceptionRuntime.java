package ua.wyverno.exceptions;

public class FileNotFoundExceptionRuntime extends RuntimeException {
    public FileNotFoundExceptionRuntime() {
        super();
    }

    public FileNotFoundExceptionRuntime(String message) {
        super(message);
    }

    public FileNotFoundExceptionRuntime(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotFoundExceptionRuntime(Throwable cause) {
        super(cause);
    }

    protected FileNotFoundExceptionRuntime(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
