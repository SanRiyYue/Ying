package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.example.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class Uservice {

    @PersistenceContext
    EntityManager entityManager;

    //Insert操作
    public User register(String email, String password, String name) {
        //创建一个User对象
        User user = new User();
        //设置好各个属性
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        //不要设置id，因为使用了自增主键
        //保存到数据库:
        entityManager.persist(user);
        //现在已经可以自动获得了Id:
        System.out.println(user.getId());
        return user;
    }

    //Delete操作
    public boolean deleteUser(Long id) {
        //Hibernate总是用id来删除记录
        User user = entityManager.find(User.class, id);//load方法返回的事一个代理对象， get方法会立即加载对象
        if (user != null) {
            entityManager.remove(user);
            return true;
        }
        return false;
    }

    //Update操作
    public void updateUser(Long id, String name) {
        User user = entityManager.find(User.class, id); //通过主键加载一个User实例
        user.setName(name); //更新其name属性
        entityManager.merge(user); //同步更新到数据库
    }

    public User fetchUserByEmail(String email) {
        //JPQL查询
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        List<User> users = query.getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users.getFirst();
    }

    public User login(String email, String password) {
        TypedQuery<User> query = entityManager.createNamedQuery("login", User.class);
        query.setParameter("e", email);
        query.setParameter("pwd", password);
        List<User> users = query.getResultList();
        return users.isEmpty() ? null : users.getFirst();
    }

    public List<User> getUsers(int pageIndex) {
        int limit = 100;
        int offset = (pageIndex - 1) * limit;
        TypedQuery<User> query = entityManager.createQuery("FROM User", User.class);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

}
