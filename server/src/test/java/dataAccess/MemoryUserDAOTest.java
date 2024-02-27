package dataAccess;

import model.UserData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class MemoryUserDAOTest {
    static UserDAO userDAO;

    @BeforeAll
    static void setup() {
        userDAO = new MemoryUserDAO();
    }

    @Test
    @Order(0)
    @DisplayName("createUser")
    void createUser() {
        //UserData user = new UserData("user1","something","something@gmail.com");
        //userDAO.createUser("user1","something","something@gmail.com");
        //Assertions.assertEquals(1,1);
    }
}