package dao;

import model.User;
import model.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UserDaoIml implements UserDao{

    public static final UserDao userDao = new UserDaoIml();

    private static final Map<Long, User> userMap = new TreeMap<>();

    static {
        userMap.put(1L,new User(1,"admin","admin", UserRole.ADMIN, true));
        userMap.put(2L,new User(2,"user","user", UserRole.CUSTOMER, true));
        userMap.put(3L,new User(3,"userTwo","userTwo", UserRole.CUSTOMER, false));
    }

    private UserDaoIml() {
    }

    public static UserDao getInstance(){
        return userDao;
    }

    @Override
    public void save(User user) {
        userMap.put(user.getId(),user);
    }

    @Override
    public void update(User user) {
        userMap.put(user.getId(),user);
    }

    @Override
    public void delete(User user) {
        userMap.remove(user.getId());
    }

    @Override
    public User findByName(String name) {
        return userMap.values().stream()
                .filter(user -> user.getUsername().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User findById(long id) {
        return userMap.get(id);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }
}
