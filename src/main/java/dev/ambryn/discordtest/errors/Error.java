package dev.ambryn.discordtest.errors;

import dev.ambryn.discordtest.enums.EError;

import java.util.List;

public record Error(EError code, String target, String message, List<Error> details) {
    public Error(EError code, String message) {
        this(code, null, message, null);
    }

    public Error(EError code, String target, String message) {
        this(code, target, message, null);
    }

    public Error(EError code, String target, String message, List<Error> details) {
        this.code = code;
        this.target = target;
        this.message = message;
        this.details = details;
    }
}
