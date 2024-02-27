package dataAccess;

import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class MemoryUserDAO implements UserDAO {

    Map<String, UserData> userTable = new HashMap<>();
    @Override
    public void createUser(String username, String password, String email) {
        userTable.put(username, new UserData(username,password,email));
    }

    @Override
    public UserData getUser(String username) {
        return userTable.get(username);
    }

    @Override
    public void clear() {
        userTable.clear();
    }
}
