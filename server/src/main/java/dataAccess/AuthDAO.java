package dataAccess;

import model.AuthData;

public interface AuthDAO {

    void createAuth(AuthData auth);

    AuthData getAuth(String authToken);

    void deleteAuth(String authToken) throws DataAccessException;
}
