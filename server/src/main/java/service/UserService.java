package service;

import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;

import javax.xml.crypto.Data;

public class UserService {
    MemoryUserDAO userDAO;
    AuthService authService;
    public UserService(MemoryUserDAO userDAO, AuthService authService) {
        this.userDAO = userDAO;
        this.authService = authService;
    }
    public AuthData register(UserData user) {
        try {
            userDAO.createUser(user);
            return authService.createAuth(user.username());
        } catch (DataAccessException e) {
            return null;
        }
    }

    public AuthData login(UserData user) {
        try {
            userDAO.getUser(user.username());
            return authService.createAuth(user.username());
        } catch (DataAccessException e) {
            return null;
        }
    }

    public void logout(UserData user) throws DataAccessException {
        userDAO.getUser(user.username());
        authService.deleteAuth(authService.authDAO.getAuthFromUsername(user.username()).authToken());
    }

}
