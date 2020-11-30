package ru.sd.app.exceptions;

public class BookShelfLoginException extends Throwable {
    private final String message;

    public BookShelfLoginException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
