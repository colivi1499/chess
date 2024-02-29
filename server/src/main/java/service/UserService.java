package service;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;

public class UserService {
    MemoryUserDAO userDAO = new MemoryUserDAO();
    AuthService authService = new AuthService();
    public AuthData register(UserData user) throws DataAccessException {
        userDAO.createUser(user);
        return authService.createAuth(user.username());
    }

    public AuthData login(UserData user) {
        return null;
    }

    public void logout(UserData user) {

    }

}
