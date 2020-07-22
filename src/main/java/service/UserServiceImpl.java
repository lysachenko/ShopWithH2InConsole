package service;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
import model.UserRole;

import java.util.List;

public class UserServiceImpl implements UserService {

    public final UserDao userDao = new UserDaoImpl();

    @Override
    public boolean login(String username, String password) {
        if (userDao.findByName(username) == null) {
            return false;
        }
        return userDao.findByName(username).getPassword().equals(password);
    }

    @Override
    public boolean register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(UserRole.CUSTOMER);
        user.setActive(true);
        userDao.save(user);
        return true;
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public void blockUser(long id) {
        User user = userDao.findById(id);
        user.setActive(false);
        userDao.update(user);
    }

    @Override
    public void unBlockUser(long id) {
        User user = userDao.findById(id);
        user.setActive(true);
        userDao.update(user);
    }

    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public User findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
