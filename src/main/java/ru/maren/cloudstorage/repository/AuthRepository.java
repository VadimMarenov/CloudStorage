package ru.maren.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maren.cloudstorage.entity.UserEntity;

@Repository
public interface AuthRepository extends JpaRepository<UserEntity, String> {
}