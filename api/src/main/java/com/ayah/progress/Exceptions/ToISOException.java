package com.ayah.progress.Exceptions;

public class ToISOException extends ProgressExceptions {

    public ToISOException() {
        super("error in ToISO value");
    }

    public ToISOException(String message) {
        super(message);
    }
}
