package pl.fastus.waluta.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.fastus.waluta.exceptions.NoSuchCurrencyException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String handleCustomException(MethodArgumentNotValidException e){
        return e.getFieldError() != null ? e.getFieldError().getDefaultMessage() : "Invalid argument";
    }

    @ExceptionHandler(NoSuchCurrencyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNoSuchElementExceptionException(NoSuchCurrencyException e){
        return e.getMessage();
    }
}
