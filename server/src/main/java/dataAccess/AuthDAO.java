package dataAccess;

import model.AuthData;

public interface AuthDAO {

    void createAuth(String username, String password);

    AuthData getAuth(String username);

    void deleteAuth(String authToken, String username);
}
