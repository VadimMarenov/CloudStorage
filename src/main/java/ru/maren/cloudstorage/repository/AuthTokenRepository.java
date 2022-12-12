package ru.maren.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maren.cloudstorage.entity.AuthTokenEntity;
import ru.maren.cloudstorage.entity.UserEntity;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthTokenEntity, String> {
    AuthTokenEntity findAuthTokenEntitiesByToken(String token);

    AuthTokenEntity findByToken(String token);
}