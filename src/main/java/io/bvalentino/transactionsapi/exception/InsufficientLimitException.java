package io.bvalentino.transactionsapi.exception;

public class InsufficientLimitException extends RuntimeException {

    public InsufficientLimitException(String message) {
        super(message);
    }

}
