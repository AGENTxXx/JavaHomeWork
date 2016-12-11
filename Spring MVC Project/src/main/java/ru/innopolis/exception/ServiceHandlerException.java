package ru.innopolis.exception;

/**
 * Created by Alexander Chuvashov on 06.12.2016.
 */
public class ServiceHandlerException extends Exception {

    public ServiceHandlerException() {
    }

    public ServiceHandlerException(String message) {
        super(message);
    }
}
