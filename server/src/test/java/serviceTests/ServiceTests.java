package serviceTests;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.UserData;
import org.junit.jupiter.api.*;
import service.AuthService;
import service.GameService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceTests {
    MemoryUserDAO userDAO = new MemoryUserDAO();
    MemoryGameDAO gameDAO = new MemoryGameDAO();
    AuthService authService = new AuthService();
    GameService gameService = new GameService(gameDAO);
    UserService userService = new UserService(userDAO,authService,gameService);

    @BeforeEach
    void setUp() throws DataAccessException {
        userService.clear();
    }

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
        assertThrows(DataAccessException.class, () -> {userService.register(new UserData("Name","something","cameron@schoeny.com"));});

        assertEquals(1,userDAO.userTable.size());
        assertTrue(userDAO.userTable.containsKey("Name"));
    }

    @Test
    @Order(2)
    @DisplayName("Login and logout")
    void loginAndOut() throws Exception {
        userService.register(new UserData("Name","something","cameron@schoeny.com"));
        userService.logout(userService.getAuthToken("Name"));
        assertEquals(1,userDAO.userTable.size());
        userService.login(new UserData("Name","something","cameron@schoeny.com"));
        assertEquals(1,userDAO.userTable.size());
        assertTrue(userDAO.userTable.containsKey("Name"));
        assertEquals(1, MemoryAuthDAO.authTable.size());
        userService.logout(userService.getAuthToken("Name"));
        assertEquals(0, MemoryAuthDAO.authTable.size());
    }

    @Test
    @Order(3)
    @DisplayName("Create game")
    void createGame() throws DataAccessException {
        userService.register(new UserData("Name","something","cameron@schoeny.com"));
        userService.gameService.createGame("Game1",authService.authDAO.getAuthFromUsername("Name").authToken());
        System.out.println(userService.gameService.listGames());
    }

    @Test
    @Order(4)
    @DisplayName("Get game")
    void getGame() throws DataAccessException {
        userService.register(new UserData("Name","something","cameron@schoeny.com"));
        userService.gameService.createGame("Game1",authService.authDAO.getAuthFromUsername("Name").authToken());
        System.out.println(userService.gameService.getGame(gameDAO.getID("Game1")));
    }

    @Test
    @Order(5)
    @DisplayName("Join game")
    void joinGame() throws DataAccessException {
        userService.register(new UserData("Name","something","cameron@schoeny.com"));
        userService.gameService.createGame("Game1",authService.authDAO.getAuthFromUsername("Name").authToken());
        userService.joinGame(ChessGame.TeamColor.WHITE,gameDAO.getID("Game1"),authService.authDAO.getAuthFromUsername("Name").authToken());
        userService.register(new UserData("Name2","something","cameron@schoeny.com"));
        userService.joinGame(ChessGame.TeamColor.BLACK,gameDAO.getID("Game1"),authService.authDAO.getAuthFromUsername("Name2").authToken());
        System.out.println(userService.gameService.getGame(gameDAO.getID("Game1")));
    }
}