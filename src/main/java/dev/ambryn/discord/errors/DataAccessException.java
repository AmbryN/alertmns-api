package dev.ambryn.discord.errors;

public class DataAccessException extends RuntimeException {
    public DataAccessException(String message, Throwable ex) {
        super(message, ex);
    }
}
