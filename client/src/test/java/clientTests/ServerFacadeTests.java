package clientTests;

import dataAccess.DataAccessException;
import org.junit.jupiter.api.*;
import result.CreateGameResult;
import result.ListGamesResult;
import server.Server;
import serverFacade.ServerFacade;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        facade.clear();
    }

    @AfterAll
    static void stopServer() throws DataAccessException {
        facade.clear();
        server.stop();
    }


    @Test
    public void register() throws DataAccessException {
        var authData = facade.register("SomeoneElse2","Password123", "johndoe@gmail.com");
        Assertions.assertTrue(authData.authToken().length() > 10);
    }

    @Test
    public void registerBadUsername() throws DataAccessException {
        facade.register("SomeoneElse2","Password123", "johndoe@gmail.com");
        Assertions.assertThrows(DataAccessException.class, () -> facade.register("SomeoneElse2", "some_password", "an_email"));
    }

    @Test
    public void login() throws DataAccessException {
        var authData = facade.register("SomeoneElse2","Password123", "johndoe@gmail.com");
        var authData2 = facade.login("SomeoneElse2","Password123");
        Assertions.assertTrue(authData.authToken().length() > 10 && authData2.authToken().length() > 10);
        Assertions.assertTrue(!authData.authToken().equals(authData2.authToken()));
    }

    @Test
    public void badLogin() throws DataAccessException {
        facade.register("SomeoneElse2","Password123", "johndoe@gmail.com");
        Assertions.assertThrows(DataAccessException.class, () -> facade.login("SomeoneElse2", "a wrong password"));
        Assertions.assertThrows(DataAccessException.class, () -> facade.login("Not a user", "Password12345"));
    }

    @Test
    public void logout() throws DataAccessException {
        var authData = facade.register("SomeoneElse2","Password123", "johndoe@gmail.com");
        facade.logout(authData.authToken());
        Assertions.assertDoesNotThrow(() -> facade.login("SomeoneElse2", "Password123"));
    }

    @Test
    public void badLogout() throws DataAccessException {
        Assertions.assertThrows(DataAccessException.class, () -> facade.logout("bad authtoken"));
    }

    @Test
    public void createGame() throws DataAccessException {
        var authData = facade.register("SomeoneElse2","Password123", "johndoe@gmail.com");
        CreateGameResult game = facade.createGame("game1",authData.authToken());
        Assertions.assertTrue(game.gameID() > 1000);
    }

    @Test
    public void createGameBad() throws DataAccessException {
        Assertions.assertThrows(DataAccessException.class, () -> facade.createGame("game1", "1234"));
    }


    @Test
    public void joinGame() throws DataAccessException {
        var authData = facade.register("SomeoneElse2","Password123", "johndoe@gmail.com");
        CreateGameResult game = facade.createGame("game1",authData.authToken());
        Assertions.assertDoesNotThrow(() -> facade.joinGame("BLACK", game.gameID(), authData.authToken()));
    }

    @Test
    public void joinGameBad() throws DataAccessException {
        var authData = facade.register("SomeoneElse2","Password123", "johndoe@gmail.com");
        CreateGameResult game = facade.createGame("game1",authData.authToken());
        facade.joinGame("BLACK", game.gameID(), authData.authToken());
        Assertions.assertThrows(DataAccessException.class, () -> facade.joinGame("BLACK", game.gameID(), authData.authToken()));
    }
    @Test
    public void listGames() throws DataAccessException {
        var authData = facade.register("SomeoneElse2","Password123", "johndoe@gmail.com");
        CreateGameResult game = facade.createGame("game1",authData.authToken());
        CreateGameResult game2 = facade.createGame("game2", authData.authToken());
        facade.joinGame("BLACK", game.gameID(), authData.authToken());
        ListGamesResult result = facade.listGames(authData.authToken());
        Assertions.assertEquals(result.games().size(), 2);
    }

    @Test
    public void listGamesBad() throws DataAccessException {
        Assertions.assertThrows(DataAccessException.class, () -> facade.listGames( "1234"));
    }


}
