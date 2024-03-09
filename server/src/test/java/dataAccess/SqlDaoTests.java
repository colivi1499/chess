package dataAccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SqlDaoTests {

    SqlAuthDAO sqlAuth = new SqlAuthDAO();
    SqlGameDAO sqlGame = new SqlGameDAO();

    public SqlDaoTests() throws DataAccessException {
    }

    @BeforeEach
    void setup() throws DataAccessException {
        sqlAuth.clear();
        sqlGame.clear();
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

    @Test
    @DisplayName("Create Game")
    @Order(9)
    void createGame() throws DataAccessException {
        sqlAuth.createAuth(new AuthData("user1", "authorization1"));
        sqlGame.createGame("Game1","authorization");
    }

    @Test
    @DisplayName("Get Game")
    @Order(10)
    void getGame() throws DataAccessException {
        sqlAuth.createAuth(new AuthData("user1", "authorization1"));
        int id = sqlGame.createGame("Game1","authorization1");
        assertEquals(sqlGame.getGame(id),new GameData(id,null,null,"Game1",new ChessGame()));
    }

    @Test
    @DisplayName("Update Game")
    @Order(11)
    void updateGame() throws DataAccessException {
        sqlAuth.createAuth(new AuthData("user1", "authorization1"));
        int id = sqlGame.createGame("Game1","authorization1");
        sqlGame.updateGame(id,new GameData(id,"Updated Game",null,"Game1",new ChessGame()));
        assertEquals(sqlGame.getGame(id),new GameData(id,"Updated Game",null,"Game1",new ChessGame()));
    }

    @Test
    @DisplayName("List Games")
    @Order(12)
    void listGames() throws DataAccessException {
        sqlAuth.createAuth(new AuthData("user1", "authorization1"));
        int id = sqlGame.createGame("Game1","authorization1");
        int id2 = sqlGame.createGame("Game2","authorization1");
        int id3 = sqlGame.createGame("Game3","authorization1");
        assertEquals(3,sqlGame.listGames().size());
    }
}
