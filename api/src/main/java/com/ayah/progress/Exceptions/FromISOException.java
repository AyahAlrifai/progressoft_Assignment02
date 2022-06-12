package com.ayah.progress.Exceptions;

public class FromISOException extends ProgressExceptions {

    public FromISOException() {
        super("error in FromIso value");
    }

    public FromISOException(String message) {
        super(message);
    }
}
