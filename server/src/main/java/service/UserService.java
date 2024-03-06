package service;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.MemoryUserDAO;
import model.AuthData;
import model.GameData;
import model.UserData;

import javax.xml.crypto.Data;

public class UserService {
    MemoryUserDAO userDAO;
    AuthService authService;
    GameService gameService;

    public UserService() {
        this(new MemoryUserDAO(), new AuthService(), new GameService());
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
        if (userDAO.getUser(user.username()).password().equals(user.password())) {
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
                if (gameService.getGame(gameID).whiteUsername() == null)
                    gameService.updateGame(gameID,new GameData(gameID, username,gameService.getGame(gameID).blackUsername(),gameService.getGame(gameID).gameName(),gameService.getGame(gameID).game()));
                else throw new DataAccessException("Bad color");
            }
            if (clientColor == ChessGame.TeamColor.BLACK) {
                if (gameService.getGame(gameID).blackUsername() == null)
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

    public void clear() {
        userDAO.clear();
        authService.clear();
        gameService.clear();
    }

}
