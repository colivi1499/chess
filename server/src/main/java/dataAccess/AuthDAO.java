package dataAccess;

import model.AuthData;

public interface AuthDAO {

    void createAuth(AuthData auth) throws DataAccessException;

    AuthData getAuth(String authToken) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;

    String getUsername(String authToken) throws DataAccessException;

    void clear() throws DataAccessException;

    String getAuthToken(String username) throws DataAccessException;

    AuthData getAuthFromUsername(String name) throws DataAccessException;
}
