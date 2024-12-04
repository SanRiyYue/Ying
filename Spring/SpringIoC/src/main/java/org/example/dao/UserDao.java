package org.example.dao;


import org.example.service.User;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Statement;
import java.util.List;

@Component
@Transactional
public class UserDao extends AbstractDao<User> {
    //直接调用父类方法:
    // User getById(long id)
    // List<User> getAll(int pageIndex)
    // deleteById(long id)
    // RowMapper<User> getRowMapper()

    public User fetchUserByEmail(String email) {
        List<User> users = getJdbcTemplate().query("SELECT * FROM users WHERE email = ?", getRowMapper(), email);
        return users.isEmpty() ? null : users.getFirst();
    }

    public User getUserByEmail(String email) {
        return getJdbcTemplate().queryForObject("SELECT * FROM users WHERE email = ?", getRowMapper(), email);
    }

    public User login(String email, String password) {
        User user = fetchUserByEmail(email);
        if (user.getPassword().equals(password)) {
            return user;
        }
        throw new RuntimeException("login failed: incorrect email or password");
    }

    public User createUser(String email, String password, String name) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        if (1 != getJdbcTemplate().update((conn) -> {
            var ps = conn.prepareStatement("INSERT INTO users (email, password, name) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setObject(1, email);
            ps.setObject(2, password);
            ps.setObject(3, name);
            return ps;
            }, keyHolder)) {
            throw new RuntimeException("insert failed.");
        }
        if ("root".equalsIgnoreCase(name)) {
            throw new RuntimeException("Invalid name, will rollback...");
        }
        return getUserByEmail(email);
    }

    public void updateUser(User user) {
        if (1 != getJdbcTemplate().update("UPDATE user SET name = ? WHERE id = ?", user.getName(), user.getId())) {
            throw new RuntimeException("update failed.");
        }
    }

}
