package dataAccess;

import model.UserData;

public class MemoryUserDAO implements UserDAO {
    @Override
    public void createUser(String username, String password) {

    }

    @Override
    public UserData getUser(String username) {
        return null;
    }

    @Override
    public void clear() {

    }
}
