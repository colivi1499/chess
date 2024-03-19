package clientTests;

import dataAccess.DataAccessException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.*;
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
    static void stopServer() {
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


}
