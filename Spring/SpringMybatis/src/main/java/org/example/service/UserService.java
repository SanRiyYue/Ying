package org.example.service;

import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class UserService {

    //注入UserMapper
    @Autowired
    UserMapper userMapper;

    public User getUserById(long id) {
        User user = userMapper.getById(id);
        if (user == null) {
            throw new RuntimeException("User not found by id.");
        }
        return user;
    }

    public  User fetchUserByEmail(String email) {
        return userMapper.getByEmail(email);
    }

    public User getUserByEmail(String email) {
        User user = userMapper.getByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found by email.");
        }
        return user;
    }


    public List<User> getAllUsers(int pageIndex) {
        int pageSize = 100;
        return userMapper.getAll((pageIndex - 1) * pageSize, pageSize);
    }

    public User login(String email, String password) {
        User user = getUserByEmail(email);
        if (user.getPassword().equals(password)) {
            return user;
        }
        throw new RuntimeException("User not found by email or password.");
    }

    public User register(String email, String password, String name) {
        if (userMapper.getByEmail(email) != null) {
            throw new RuntimeException("User is already registered.");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setCreatedAt(System.currentTimeMillis());
        userMapper.insert(user);
        return user;
    }

    public void updateUser(Long id, String name) {
        User user = userMapper.getById(id);
        if (user == null) {
            throw new RuntimeException("User not found by id.");
        }
        user.setName(name);
        user.setCreatedAt(System.currentTimeMillis());
        userMapper.update(user);
    }

    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }


}
