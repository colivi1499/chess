package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import model.AuthData;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthService {
    MemoryAuthDAO authDAO = new MemoryAuthDAO();
    public AuthData createAuth(String username) throws DataAccessException {
        AuthData auth = new AuthData(generateAuthToken(username),username);
        authDAO.createAuth(auth);
        return authDAO.getAuth(auth.authToken());
    }

    public void deleteAuth(String authToken) {
        authDAO.deleteAuth(authToken);
    }
    private String generateAuthToken(String username) {
        String token = username + "_" + System.currentTimeMillis();
        byte[] bytes = Base64.getEncoder().encode(token.getBytes(StandardCharsets.UTF_8));
        return new String(bytes,StandardCharsets.UTF_8);
    }
}
