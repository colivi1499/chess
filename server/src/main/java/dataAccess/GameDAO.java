package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.Map;

public interface GameDAO {


    void createGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game);

    GameData getGame(int gameID) throws DataAccessException;

    Map<Integer,GameData> listGames();

    void updateGame(ChessGame newGame);
}
