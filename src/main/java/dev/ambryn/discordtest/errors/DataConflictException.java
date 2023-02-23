package dev.ambryn.discordtest.errors;

public class DataConflictException extends RuntimeException {
    public DataConflictException(Throwable ex) {
        super(ex);
    }
}
