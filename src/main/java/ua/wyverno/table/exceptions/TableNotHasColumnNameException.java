package ua.wyverno.table.exceptions;

public class TableNotHasColumnNameException extends RuntimeException {
    public TableNotHasColumnNameException() {
        super();
    }

    public TableNotHasColumnNameException(String message) {
        super(message);
    }

    public TableNotHasColumnNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableNotHasColumnNameException(Throwable cause) {
        super(cause);
    }

    public TableNotHasColumnNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
