package ru.maren.cloudstorage.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.maren.cloudstorage.exception.AuthorizationException;
import ru.maren.cloudstorage.exception.BadCredentialException;
import ru.maren.cloudstorage.exception.FileNotFoundException;
import ru.maren.cloudstorage.model.responce.ExceptionResponse;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialException.class)
    public ExceptionResponse bceHandler(@NonNull final BadCredentialException exc) {
        log.error(exc.getMessage());
        return new ExceptionResponse(exc.getMessage(), 400);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthorizationException.class)
    public ExceptionResponse aeHandler(@NonNull final AuthorizationException exc) {
        log.error(exc.getMessage());
        return new ExceptionResponse(exc.getMessage(), 401);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FileNotFoundException.class)
    public ExceptionResponse fnfHandler(@NonNull final FileNotFoundException exc) {
        log.error(exc.getMessage());
        return new ExceptionResponse(exc.getMessage(), 400);
    }
}

