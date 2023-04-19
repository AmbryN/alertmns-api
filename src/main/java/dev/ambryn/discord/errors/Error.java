package dev.ambryn.discord.errors;

import dev.ambryn.discord.enums.EError;

import java.util.List;

public class Error {
    EError code;
    String target;
    String value;
    String message;
    List<Error> details;

    private Error() {
        this(null, null, null, null, null);
    }

    private Error(EError code, String message) {
        this(code, null, null, message, null);
    }

    private Error(EError code, String target, String value, String message) {
        this(code, target, message, value, null);
    }

    private Error(EError code, String target, String value, String message, List<Error> details) {
        this.code = code;
        this.target = target;
        this.value = value;
        this.message = message;
        this.details = details;
    }

    public EError getCode() {
        return code;
    }

    private void setCode(EError code) {
        this.code = code;
    }

    public String getTarget() {
        return target;
    }

    private void setTarget(String target) {
        this.target = target;
    }

    public String getValue() {
        return value;
    }

    private void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public List<Error> getDetails() {
        return details;
    }

    private void setDetails(List<Error> details) {
        this.details = details;
    }



    public static class Builder {
        private final Error error;

        public Builder() {
            this.error = new Error();
        }

        public Builder setCode(EError errorCode) {
            error.setCode(errorCode);
            return this;
        }

        public Builder setTarget(String target) {
            error.setTarget(target);
            return this;
        }

        public Builder setValue(String value) {
            error.setValue(value);
            return this;
        }

        public Builder setMessage(String message) {
            error.setMessage(message);
            return this;
        }

        public Builder setDetails(List<Error> sub) {
            error.setDetails(sub);
            return this;
        }

        public Error build() {
            return error;
        }
    }
}
