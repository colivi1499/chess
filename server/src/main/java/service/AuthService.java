package service;

import dataAccess.MemoryAuthDAO;
import model.AuthData;

public class AuthService {
    MemoryAuthDAO authDAO = new MemoryAuthDAO();
    public AuthData createAuth(String username) {
        AuthData auth = new AuthData("12345",username);
        authDAO.createAuth(auth);
        return authDAO.getAuth(auth.authToken());
    }
}
