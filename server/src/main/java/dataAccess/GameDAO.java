package dataAccess;

import chess.ChessGame;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.Collection;

public class GameDAO {

    private Collection<ChessGame> gameList;

    public GameDAO() {
        gameList = new ArrayList<>();
    }

    private void createGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
    }

    private GameData getGame(int gameID) {
        return new GameData(0,"", "", "", new ChessGame());
    }

    private Collection<ChessGame> listGames() {
        return gameList;
    }

    private void updateGame(ChessGame newGame) {

    }
}
