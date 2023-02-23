package dev.ambryn.discordtest.errors;

public class DataAccessException extends RuntimeException {
    public DataAccessException(Exception ex) {
        super(ex);
    }
}
