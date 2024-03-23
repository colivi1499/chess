package service;

import chess.ChessGame;
import dataAccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

public class UserService {
    UserDAO userDAO;
    AuthService authService;
    public GameService gameService;

    public UserService() {
        this(new MemoryUserDAO(), new AuthService(), new GameService());
    }

    public UserService(UserDAO userDAO) throws DataAccessException {
        this.userDAO = userDAO;
        this.authService = new AuthService(new SqlAuthDAO());
        this.gameService = new GameService(new SqlGameDAO());
    }

    public UserService(MemoryUserDAO userDAO, AuthService authService, GameService gameService) {
        this.userDAO = userDAO;
        this.authService = authService;
        this.gameService = gameService;
    }
    public AuthData register(UserData user) throws DataAccessException {
        userDAO.createUser(user);
        return authService.createAuth(user.username());
    }

    public AuthData login(UserData user) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(user.password(),userDAO.getUser(user.username()).password())) {
            return authService.createAuth(user.username());
        }
        throw new Exception("Incorrect password");
    }

    public void logout(String authToken) throws DataAccessException {
        authService.deleteAuth(authToken);
    }

    public void joinGame(ChessGame.TeamColor clientColor, int gameID, String authToken, String username) throws DataAccessException {
        if (authService.authDAO.getAuth(authToken) != null && gameService.getGame(gameID) != null) {
            if (clientColor == ChessGame.TeamColor.WHITE) {
                if (gameService.getGame(gameID).whiteUsername() == null || Objects.equals(gameService.getGame(gameID).whiteUsername(), username))
                    gameService.updateGame(gameID,new GameData(gameID, username,gameService.getGame(gameID).blackUsername(),gameService.getGame(gameID).gameName(),gameService.getGame(gameID).game()));
                else throw new DataAccessException("Bad color");
            }
            if (clientColor == ChessGame.TeamColor.BLACK) {
                if (gameService.getGame(gameID).blackUsername() == null || Objects.equals(gameService.getGame(gameID).blackUsername(), username))
                    gameService.updateGame(gameID,new GameData(gameID, gameService.getGame(gameID).whiteUsername(),username,gameService.getGame(gameID).gameName(),gameService.getGame(gameID).game()));
                else throw new DataAccessException("Bad color");
            }
        }
    }

    public String getAuthToken(String username) throws DataAccessException {
        return authService.authDAO.getAuthToken(username);
    }

    public String getUsername(String authToken) throws DataAccessException {
        return authService.getUsername(authToken);
    }

    public void clear() throws DataAccessException {
        userDAO.clear();
        authService.clear();
        gameService.clear();
    }

}
