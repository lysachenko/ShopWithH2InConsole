package dao;

import model.User;

import java.util.List;

public interface UserDao {
    void save(User user);

    void update(User user);

    void delete(User user);

    User findByName(String name);

    User findById(long id);

    List<User> findAll();
}
