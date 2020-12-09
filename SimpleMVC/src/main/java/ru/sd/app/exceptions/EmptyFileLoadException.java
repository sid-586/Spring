package ru.sd.app.exceptions;

public class EmptyFileLoadException extends Exception {
    private final String message;

    public EmptyFileLoadException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
