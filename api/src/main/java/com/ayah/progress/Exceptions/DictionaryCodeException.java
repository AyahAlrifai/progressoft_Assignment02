package com.ayah.progress.Exceptions;

public class DictionaryCodeException extends ProgressExceptions {

    public DictionaryCodeException() {
        super("error on dictionary code");
    }

    public DictionaryCodeException(String message) {
        super(message);
    }
}
