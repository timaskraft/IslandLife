package com.timas.projects.exeptions;

public class InitException extends Exception {

    public InitException(String message, Exception e) {
        super(message, e);
    }
}
