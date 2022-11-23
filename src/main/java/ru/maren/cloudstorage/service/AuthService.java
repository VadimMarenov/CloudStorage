package ru.maren.cloudstorage.service;

import org.springframework.stereotype.Service;
import ru.maren.cloudstorage.entity.AuthTokenEntity;
import ru.maren.cloudstorage.entity.UserEntity;
import ru.maren.cloudstorage.exception.AuthorizationException;
import ru.maren.cloudstorage.exception.BadCredentialException;
import ru.maren.cloudstorage.model.request.LoginRequest;
import ru.maren.cloudstorage.model.responce.LoginResponse;
import ru.maren.cloudstorage.repository.AuthRepository;
import ru.maren.cloudstorage.repository.AuthTokenRepository;

import java.util.Random;

@Service
public class AuthService {
    private final AuthRepository authRepository;
    private final AuthTokenRepository authTokenRepository;
    private final Random random = new Random();

    public AuthService(AuthRepository authRepository, AuthTokenRepository authTokenRepository) {
        this.authRepository = authRepository;
        this.authTokenRepository = authTokenRepository;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        final String loginFromRequest = loginRequest.getLogin();
        final UserEntity user = authRepository.findById(loginFromRequest).orElseThrow(() ->
                new BadCredentialException("User with login " + loginFromRequest + " not found"));

        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new BadCredentialException("Incorrect password for user " + loginFromRequest);
        }
        final String authToken = String.valueOf(random.nextLong());
        authTokenRepository.save(new AuthTokenEntity(authToken));
        return new LoginResponse(authToken);
    }

    public void logout(AuthTokenEntity authToken) {
        authTokenRepository.deleteById(authToken.getToken());
    }

    public void checkToken(String authToken) {
        final String authTokenWithoutBearer = authToken.split(" ")[1];
        if (!authTokenRepository.existsById(authTokenWithoutBearer)) {
            throw new AuthorizationException("User is not authorized");
        }
    }
}
