package ru.maren.cloudstorage.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.maren.cloudstorage.exception.AuthorizationException;
import ru.maren.cloudstorage.exception.BadCredentialException;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BadCredentialException.class)
    public ResponseEntity<String> bceHandler(@NonNull final BadCredentialException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exc.getMessage());
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<String> aeHandler(@NonNull final AuthorizationException exc){
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exc.getMessage());
    }
}

