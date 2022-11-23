package ru.maren.cloudstorage.model.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditedFileNameRequest {
    private String filename;

    @JsonCreator
    public EditedFileNameRequest(String filename) {
        this.filename = filename;
    }
}
