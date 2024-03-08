package dataAccess;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SqlDaoTests {

    SqlAuthDAO sqlAuth = new SqlAuthDAO();

    public SqlDaoTests() throws DataAccessException {
    }

    @BeforeEach
    void setup() throws DataAccessException {
        sqlAuth.clear();
    }
    @Test
    @DisplayName("Positive create auth")
    @Order(1)
    void createAuth() throws DataAccessException {
        sqlAuth.createAuth(new AuthData("user1", "authorization"));
    }

    @Test
    @DisplayName("Negative create auth")
    @Order(2)
    void createDuplicateAuth() throws DataAccessException {
        sqlAuth.createAuth(new AuthData("user1", "authorization"));
        assertThrows(DataAccessException.class, () -> {sqlAuth.createAuth(new AuthData("user1", "authorization"));});
    }

    @Test
    @DisplayName("Clear")
    @Order(3)
    void clearAuth() throws DataAccessException {
        sqlAuth.createAuth(new AuthData("user1", "authorization1"));
        sqlAuth.createAuth(new AuthData("user2", "authorization2"));
        sqlAuth.clear();
    }

    @Test
    @DisplayName("Get Auth")
    @Order(4)
    void getAuth() throws DataAccessException {
        sqlAuth.createAuth(new AuthData("user1", "authorization1"));
        assertEquals(new AuthData("user1","authorization1"),sqlAuth.getAuth("authorization1"));
    }


    @Test
    @DisplayName("Get Auth from username")
    @Order(5)
    void getAuthFromUsername() throws DataAccessException {
        sqlAuth.createAuth(new AuthData("user1", "authorization1"));
        assertEquals("authorization1",sqlAuth.getAuthToken("user1"));
    }

    @Test
    @DisplayName("Get Auth token from username")
    @Order(6)
    void getAuthTokenFromUsername() throws DataAccessException {
        sqlAuth.createAuth(new AuthData("user1", "authorization1"));
        assertEquals(new AuthData("user1","authorization1"),sqlAuth.getAuthFromUsername("user1"));
    }

    @Test
    @DisplayName("Get username token from authToken")
    @Order(7)
    void getUsername() throws DataAccessException {
        sqlAuth.createAuth(new AuthData("user1", "authorization1"));
        assertEquals("user1",sqlAuth.getUsername("authorization1"));
    }

    @Test
    @DisplayName("Delete auth token")
    @Order(8)
    void deleteAuth() throws DataAccessException {
        sqlAuth.createAuth(new AuthData("user1", "authorization1"));
        sqlAuth.deleteAuth("authorization1");
        sqlAuth.createAuth(new AuthData("user1", "authorization1"));
    }
}
