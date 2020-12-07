package ru.sd.app.exceptions;

public class EmptyFileLoadException extends Throwable {
    private final String message;

    public EmptyFileLoadException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
