package service;

import dataAccess.MemoryGameDAO;
import dataAccess.MemoryUserDAO;
import model.GameData;
import model.UserData;

import java.util.Collection;

public class GameService {
    static MemoryGameDAO gameDAO;

    public GameService() {
        this(new MemoryGameDAO());
    }

    public GameService(MemoryGameDAO gameDAO) {
        GameService.gameDAO = gameDAO;
    }

    public void createGame(String gameName, String authToken) {
        gameDAO.createGame(gameName,authToken);
    }

    public Collection<GameData> listGames() {
        return gameDAO.listGames().values();
    }

    public GameData getGame(int gameID) {
        return gameDAO.getGame(gameID);
    }

    public void updateGame(int gameID, GameData newGame) {
        gameDAO.updateGame(gameID,newGame);
    }

    public void clear() {
        gameDAO.clear();
    }

}
