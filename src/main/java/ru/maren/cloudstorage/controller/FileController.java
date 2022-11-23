package ru.maren.cloudstorage.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.maren.cloudstorage.model.responce.ListFileResponse;
import ru.maren.cloudstorage.service.AuthService;
import ru.maren.cloudstorage.service.FileService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
public class FileController {
    private final FileService fileService;
    private final AuthService authService;

    @PostMapping("/file")
    public void uploadFile(@RequestHeader("auth-token") @NotBlank String authToken, @NotBlank String filename, @NotNull @RequestBody MultipartFile file) throws IOException {
        authService.checkToken(authToken);
        fileService.addFile(filename, file.getBytes());
    }

    @DeleteMapping("/file")
    public void deleteFile(@RequestHeader("auth-token") @NotBlank String authToken, @NotBlank String filename) {
        authService.checkToken(authToken);
        fileService.deleteFile(filename);

    }

    @GetMapping(value = "/file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getFile(@RequestHeader("auth-token") @NotBlank String authToken, @NotBlank String filename) {
        authService.checkToken(authToken);
        return fileService.getFile(filename);
    }

    @PutMapping("/file")
    public void editFile(@RequestHeader("auth-token") @NotBlank String authToken, @NotBlank String filename, @RequestBody String editedFileName) {
        authService.checkToken(authToken);
        fileService.editFileName(filename, editedFileName);
    }

    @GetMapping("/list")
    public List<ListFileResponse> listFiles(@RequestHeader("auth-token") @NotBlank String authToken, @NotBlank int limit) {
        authService.checkToken(authToken);
        return fileService.listFiles(limit);
    }
}
