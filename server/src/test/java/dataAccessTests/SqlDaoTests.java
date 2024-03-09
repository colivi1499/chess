package dataAccessTests;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SqlDaoTests {

    SqlAuthDAO sqlAuth = new SqlAuthDAO();
    SqlGameDAO sqlGame = new SqlGameDAO();
    SqlUserDAO sqlUser = new SqlUserDAO();

    public SqlDaoTests() throws DataAccessException {
    }

    @BeforeEach
    void setup() throws DataAccessException {
        sqlAuth.clear();
        sqlGame.clear();
        sqlUser.clear();
    }
    @Test
    @DisplayName("Positive create auth")
    @Order(1)
    void createAuth() throws DataAccessException {
        sqlAuth.createAuth(new AuthData("user1", "authorization"));
        assertEquals(new AuthData("user1","authorization"),sqlAuth.getAuth("authorization"));
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
        sqlGame.createGame("game","authorization1");
        sqlUser.createUser(new UserData("Cameron","Password123","email@gmail.com"));
        sqlAuth.clear();
        sqlUser.clear();
        sqlGame.clear();
        assertEquals(0,sqlGame.listGames().size());
        assertThrows(DataAccessException.class, () -> {sqlUser.getUser("Cameron");});
        assertThrows(DataAccessException.class, () -> {sqlAuth.getAuth("authorization1");});
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
        assertThrows(DataAccessException.class, () -> {sqlAuth.getAuth("authorization1");});
    }

    @Test
    @DisplayName("Create Game")
    @Order(9)
    void createGame() throws DataAccessException {
        sqlAuth.createAuth(new AuthData("user1", "authorization1"));
        sqlGame.createGame("Game1","authorization1");
        assertEquals(1,sqlGame.listGames().size());
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

    @Test
    @DisplayName("Create User")
    @Order(13)
    void createUser() throws DataAccessException {
        sqlUser.createUser(new UserData("Cameron","pioneer47","cameron@schoeny.com"));
        assertEquals("Cameron", sqlUser.getUser("Cameron").username());
    }

    @Test
    @DisplayName("Get User")
    @Order(14)
    void getUser() throws DataAccessException {
        sqlUser.createUser(new UserData("Cameron","pioneer47","cameron@schoeny.com"));
        var user = sqlUser.getUser("Cameron");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches("pioneer47",user.password()));
    }

    @Test
    @DisplayName("Get User Negative")
    @Order(15)
    void getNonexistentUser() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {var user = sqlUser.getUser("Cameron");});
    }

    @Test
    @DisplayName("Create User that already exists")
    @Order(16)
    void createUserAlreadyExists() throws DataAccessException {
        sqlUser.createUser(new UserData("Cameron","pioneer47","cameron@schoeny.com"));
        assertThrows(DataAccessException.class, () -> {sqlUser.createUser(new UserData("Cameron","pioneer47","cameron@schoeny.com"));});
    }

    @Test
    @DisplayName("List games when there are no games")
    @Order(17)
    void listGamesEmpty() throws DataAccessException {
        assertEquals(0,sqlGame.listGames().size());
    }

    @Test
    @DisplayName("Update Game Invalid ID")
    @Order(18)
    void updateGameInvalidID() throws DataAccessException {
        sqlAuth.createAuth(new AuthData("user1", "authorization1"));
        int id = sqlGame.createGame("Game1","authorization1");
        assertThrows(DataAccessException.class, () -> {sqlGame.updateGame(-1,new GameData(0,"Updated Game",null,"Game1",new ChessGame()));});
    }

    @Test
    @DisplayName("Get Game that doesn't exist")
    @Order(19)
    void getGameInvalid() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {sqlGame.getGame(-12);});
    }

    @Test
    @DisplayName("Create Game Invalid auth token")
    @Order(20)
    void createGameInvalid() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {sqlGame.createGame("Game1","auth");});
    }

    @Test
    @DisplayName("Delete invalid auth token")
    @Order(21)
    void deleteAuthInvalid() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {sqlAuth.deleteAuth("authorization1");});
    }

    @Test
    @DisplayName("Get username token from an invalid authToken")
    @Order(22)
    void getUsernameInvalid() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {sqlAuth.getUsername("authorization1");});
    }

    @Test
    @DisplayName("Get Auth token from invalid username")
    @Order(23)
    void getAuthTokenFromUsernameInvalid() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {sqlAuth.getAuthToken("user");});
    }

    @Test
    @DisplayName("Get Auth from invalid username")
    @Order(24)
    void getAuthFromUsernameInvalid() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {sqlAuth.getAuthFromUsername("user");});
    }

    @Test
    @DisplayName("Get invalid authtoken")
    @Order(25)
    void getAuthInvalid() throws DataAccessException {
        assertThrows(DataAccessException.class, () -> {sqlAuth.getAuth("authorization1");});
    }
}
