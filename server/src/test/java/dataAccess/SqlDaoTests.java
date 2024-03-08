package dataAccess;

import model.AuthData;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SqlDaoTests {

    @Test
    @DisplayName("Positive create auth")
    @Order(1)
    void createAuth() throws DataAccessException {
        SqlAuthDAO sqlAuth = new SqlAuthDAO();
        sqlAuth.createAuth(new AuthData("user1", "authorization"));
    }
}
