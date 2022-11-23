package ru.maren.cloudstorage.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.maren.cloudstorage.entity.FileEntity;
import ru.maren.cloudstorage.model.responce.ListFileResponse;
import ru.maren.cloudstorage.repository.FileRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    public void addFile(String filename, byte[] fileContent) {
        fileRepository.save(new FileEntity(filename, fileContent));
    }

    public void deleteFile(String filename) {
        checkFileExist(filename);
        fileRepository.deleteById(filename);
    }

    public byte[] getFile(String filename) {
        checkFileExist(filename);
        FileEntity file = fileRepository.findById(filename)
                .orElseThrow(() -> new RuntimeException("File " + filename + " not found"));
        return file.getFileContent();
    }

    public void editFileName(String filename, String editedFileName) {
        checkFileExist(filename);
        FileEntity file = fileRepository.findById(filename)
                .orElseThrow(() -> new RuntimeException("File " + filename + " not found"));
        FileEntity editedFile = new FileEntity(editedFileName, file.getFileContent());
        fileRepository.save(editedFile);
        fileRepository.delete(file);
    }

    private void checkFileExist(String filename) {
        if (!fileRepository.existsById(filename)) {
            throw new RuntimeException("File " + filename + " not found");
        }
    }

    public List<ListFileResponse> listFiles(int limit) {
        final List<FileEntity> files = fileRepository.getFiles(limit);
        return files.stream()
                .map(file -> new ListFileResponse(file.getFileName(), file.getFileContent().length))
                .collect(Collectors.toList());
    }

}

