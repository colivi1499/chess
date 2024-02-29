package service;

import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserService userService = new UserService();

    @Test
    @Order(0)
    void registerTest() throws DataAccessException {
        AuthData authData = userService.register(new UserData("Name","something","cameron@schoeny.com"));
        System.out.println(authData);

    }

    @Test
    @Order(1)
    @DisplayName("Duplicate name")
    void name() throws DataAccessException {
        userService.register(new UserData("Name","something","cameron@schoeny.com"));
        userService.register(new UserData("Name","something else", "cameron@schoeny.com"));
        userService.register(new UserData("Name","something else", "cameron@schoeny.com"));
    }
}