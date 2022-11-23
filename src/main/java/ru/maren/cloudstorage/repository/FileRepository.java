package ru.maren.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.maren.cloudstorage.entity.FileEntity;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {
    @Query(value = "SELECT * FROM files LIMIT :limit", nativeQuery = true)
    List<FileEntity> getFiles(int limit);

}