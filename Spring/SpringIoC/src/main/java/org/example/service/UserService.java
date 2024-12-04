package org.example.service;

import org.example.dao.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Component
@Transactional
public class UserService {

    @Autowired
    UserDao userDao;

    public User getUserById(int id) {
        return userDao.getById((id));
    }

    public User getUserByName(String name) {
        return null;
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public User fetchUserByEmail(String email) {
        return userDao.fetchUserByEmail(email);
    }

    public void deleteUserById(long id) {
        userDao.deleteById(id);
    }

    public long getUsers() {
        return userDao.getJdbcTemplate().queryForObject("SELECT COUNT(*) FROM users", userDao.getRowMapper()).getId();
    }

    public List<User> getUsers(int pageIndex) {
        return userDao.getAll(pageIndex);
    }

    public User login (String email, String password) {
        return userDao.login(email, password);
    }

    public User register (String email, String password, String name) {
        return userDao.createUser(email, password, name);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }
}
