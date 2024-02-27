package dataAccess;

import model.AuthData;

public class MemoryAuthDAO implements AuthDAO {
    @Override
    public void createAuth(String username, String password) {

    }

    @Override
    public AuthData getAuth(String username) {
        return null;
    }

    @Override
    public void deleteAuth(String authToken, String username) {

    }
}
