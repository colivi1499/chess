package service;

import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    MemoryUserDAO userDAO = new MemoryUserDAO();
    AuthService authService = new AuthService();
    UserService userService = new UserService(userDAO,authService);

    @Test
    @Order(0)
    @DisplayName("Register names")
    void registerTest() throws DataAccessException {
        userService.register(new UserData("Name","something","cameron@schoeny.com"));
        userService.register(new UserData("Name2","something","cameron@schoeny.com1"));
        assertEquals(2,userDAO.userTable.size());
        assertTrue(userDAO.userTable.containsKey("Name"));
        assertTrue(userDAO.userTable.containsKey("Name2"));
    }

    @Test
    @Order(1)
    @DisplayName("Duplicate name")
    void registerDuplicateNames() throws DataAccessException {
        userService.register(new UserData("Name","something","cameron@schoeny.com"));
        userService.register(new UserData("Name","something","cameron@schoeny.com"));
        assertEquals(1,userDAO.userTable.size());
        assertTrue(userDAO.userTable.containsKey("Name"));
    }

    @Test
    @Order(2)
    @DisplayName("Login")
    void login() throws DataAccessException {
        userService.register(new UserData("Name","something","cameron@schoeny.com"));
        userService.logout(new UserData("Name", "something", "cameron@schoeny.com"));
        assertEquals(1,userDAO.userTable.size());
        userService.login(new UserData("Name","something","cameron@schoeny.com"));
        assertEquals(1,userDAO.userTable.size());
        assertTrue(userDAO.userTable.containsKey("Name"));
    }
}