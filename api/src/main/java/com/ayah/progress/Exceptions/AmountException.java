package com.ayah.progress.Exceptions;

public class AmountException extends ProgressExceptions {

    public AmountException() {
        super("error in amount value");
    }

    public AmountException(String message) {
        super(message);
    }
    
}
