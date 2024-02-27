package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public interface GameDAO {


    void createGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game);

    GameData getGame(int gameID);

    Collection<ChessGame> listGames();

    void updateGame(ChessGame newGame);
}
