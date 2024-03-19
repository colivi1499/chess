package clientTests;

import dataAccess.DataAccessException;
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

}
