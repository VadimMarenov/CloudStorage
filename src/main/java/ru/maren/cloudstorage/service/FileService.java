package ru.maren.cloudstorage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.maren.cloudstorage.entity.AuthTokenEntity;
import ru.maren.cloudstorage.entity.FileEntity;
import ru.maren.cloudstorage.model.responce.ListFileResponse;
import ru.maren.cloudstorage.repository.AuthTokenRepository;
import ru.maren.cloudstorage.repository.FileRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final AuthTokenRepository authTokenRepository;

    public void uploadFile(String authToken, String filename, byte[] fileContent) {
        String login = getLoginByToken(authToken);
        fileRepository.save(new FileEntity(UUID.randomUUID(), filename, fileContent, fileContent.length, login));
    }

    private String getLoginByToken(String authToken) {
        AuthTokenEntity tokenEntity = authTokenRepository.findByToken(authToken);
        return tokenEntity.getLogin();
    }

    public void deleteFile(String authToken, String filename) {
        String login = getLoginByToken(authToken);
        fileRepository.deleteByLoginAndFileName(login, filename);
    }

    public byte[] getFile(String authToken, String filename) {
        String login = getLoginByToken(authToken);
        FileEntity file = fileRepository.findByLoginAndFileName(login, filename)
                .orElseThrow(() -> new RuntimeException("File " + filename + " not found"));
        return file.getFileContent();
    }

    public void editFileName(String authToken, String filename, String editedFileName) {
        String login = getLoginByToken(authToken);

        FileEntity file = fileRepository.findByLoginAndFileName(login, filename)
                .orElseThrow(() -> new RuntimeException("File " + filename + " not found"));
        FileEntity editedFile = new FileEntity(UUID.randomUUID(), editedFileName, file.getFileContent(), file.getSize(), login);
        fileRepository.save(editedFile);
        fileRepository.delete(file);
    }

    public List<ListFileResponse> getAllFiles(String authToken, int limit) {
        String login = getLoginByToken(authToken);
        final List<FileEntity> files = fileRepository.findAllByLogin(login);
        return files.stream()
                .map(file -> new ListFileResponse(file.getFileName(), file.getSize()))
                .collect(Collectors.toList());
    }


}

