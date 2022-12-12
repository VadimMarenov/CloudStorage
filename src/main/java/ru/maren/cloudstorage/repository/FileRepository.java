package ru.maren.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maren.cloudstorage.entity.FileEntity;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, String> {
    List<FileEntity> findAllByLogin(String login);

    Optional<FileEntity> findByLoginAndFileName(String login, String filename);

    @Transactional
    void deleteByLoginAndFileName(String login, String filename);

}