package ru.maren.cloudstorage.model.responce;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListFileResponse {
    private String fileName;
    private int size;
}
