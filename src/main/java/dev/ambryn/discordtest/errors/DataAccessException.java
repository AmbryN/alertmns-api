package dev.ambryn.discordtest.errors;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String message, Throwable ex) {
        super(message, ex);
    }
}
