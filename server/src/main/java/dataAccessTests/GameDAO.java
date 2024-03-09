package dataAccessTests;

import model.GameData;

import java.util.Map;

public interface GameDAO {



    int createGame(String gameName, String authToken) throws DataAccessException;

    GameData getGame(int gameID) throws DataAccessException;

    Map<Integer,GameData> listGames() throws DataAccessException;

    void updateGame(int gameID, GameData newGame) throws DataAccessException;

    void clear() throws DataAccessException;
}
