package ru.maren.cloudstorage.model.responce;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private final String message;
    private final int id;
}