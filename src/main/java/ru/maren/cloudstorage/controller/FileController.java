package ru.maren.cloudstorage.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.maren.cloudstorage.model.request.EditedFileNameRequest;
import ru.maren.cloudstorage.model.responce.ListFileResponse;
import ru.maren.cloudstorage.service.AuthService;
import ru.maren.cloudstorage.service.FileService;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class FileController {
    private final FileService fileService;
    private final AuthService authService;

    @PostMapping("/file")
    ResponseEntity uploadFile(@RequestHeader("auth-token") String authToken, @RequestParam("filename") String filename, MultipartFile file) throws IOException {
        authService.checkToken(authToken);
        byte[] fileContent = file.getBytes();
        fileService.uploadFile(filename, fileContent);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public ResponseEntity deleteFile(@RequestHeader("auth-token") String authToken, String filename) {
        authService.checkToken(authToken);
        fileService.deleteFile(filename);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(value = "/file")
    public ResponseEntity<Resource> getFile(@RequestHeader("auth-token") String authToken, String filename) {
        authService.checkToken(authToken);
        byte[] file = fileService.getFile(filename);
        return ResponseEntity.ok().body(new ByteArrayResource(file));
    }

    @PutMapping("/file")
    public void editFile(@RequestHeader("auth-token") String authToken, String filename, @RequestBody EditedFileNameRequest newFilename) {
        authService.checkToken(authToken);
        fileService.editFileName(filename, newFilename.getFilename());
    }

    @GetMapping("/list")
    public List<ListFileResponse> listFiles(@RequestHeader("auth-token") String authToken, @RequestParam("limit") Integer limit) {
        authService.checkToken(authToken);
        return fileService.getAllFiles(limit);
    }
}
