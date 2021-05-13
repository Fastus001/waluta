package pl.fastus.waluta.exceptions;

public class NoSuchCurrencyException extends RuntimeException{

    public NoSuchCurrencyException(String message) {
        super(message);
    }
}
