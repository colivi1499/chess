package dataAccess;

import model.GameData;

import java.util.Map;

public interface GameDAO {



    int createGame(String gameName, String authToken) throws DataAccessException;

    GameData getGame(int gameID) throws DataAccessException;

    Map<Integer,GameData> listGames();

    void updateGame(int gameID, GameData newGame);

    void clear() throws DataAccessException;
}
