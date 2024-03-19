package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import model.AuthData;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthService {
    public AuthDAO authDAO;

    public AuthService() {
        this(new MemoryAuthDAO());
    }

    public AuthService(AuthDAO authDAO) {
        this.authDAO = authDAO;
    }
    public AuthData createAuth(String username) throws DataAccessException {
        AuthData auth = new AuthData(username, generateAuthToken(username));
        authDAO.createAuth(auth);
        return authDAO.getAuth(auth.authToken());
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        authDAO.deleteAuth(authToken);
    }
    private String generateAuthToken(String username) {
        String token = username + "_" + System.currentTimeMillis();
        byte[] bytes = Base64.getEncoder().encode(token.getBytes(StandardCharsets.UTF_8));
        return new String(bytes,StandardCharsets.UTF_8);
    }

    public void clear() throws DataAccessException {
        authDAO.clear();
    }

    public String getUsername(String authToken) throws DataAccessException {
        return authDAO.getUsername(authToken);
    }
}
