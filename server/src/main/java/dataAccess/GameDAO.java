package dataAccess;

import chess.ChessGame;
import model.GameData;
import model.UserData;

import java.util.Collection;
import java.util.Map;

public interface GameDAO {



    void createGame(String gameName, String authToken, UserData user);

    GameData getGame(int gameID) throws DataAccessException;

    Map<Integer,GameData> listGames();

    void updateGame(int gameID, GameData newGame) throws DataAccessException;
}
