package br.com.fbourguignon.application.exception;

public class ProcessSQSEventException extends RuntimeException {
    public ProcessSQSEventException(String message) {
        super(message);
    }
}
