package dataAccess;

import model.AuthData;

public class SqlAuthDAO implements AuthDAO {
    @Override
    public void createAuth(AuthData auth) throws DataAccessException {

    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }

    @Override
    public String getUsername(String authToken) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public String getAuthToken(String username) throws DataAccessException {
        return null;
    }

    @Override
    public AuthData getAuthFromUsername(String name) throws DataAccessException {
        return null;
    }
}
