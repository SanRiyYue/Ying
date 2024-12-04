package org.example.entity;

import jakarta.persistence.*;

@NamedQueries(
        @NamedQuery(
                //查询名称:
                name = "login",
                //查询语句:
                query = "SELECT u FROM User u WHERE u.email = :e AND u.password = :pwd"
        )
)

//表明是一个实体类，将User类映射到数据库中的一个表
@Entity
//@Table(name = "users")
public class User extends AbstractEntity {

    //均使用包装类型

    private String email;
    private String password;
    private String name;

    public User() {}

    public User(Long id, String email, String password, String name) {
        setId(id);
        setEmail(email);
        setPassword(password);
        setName(name);
    }

    //getter字段

    @Column(nullable = false, unique = true, length = 100)
    public String getEmail() {
        return email;
    }

    @Column(nullable = false, length = 100)
    public String getPassword() {
        return password;
    }

    @Column(nullable = false, length = 100)
    public String getName() {
        return name;
    }

    //Setter字段

    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "User [id=" + getId() + ", email=" + getEmail() + ", password=" + getEmail() + ", name=" + getName() + "]";
    }
}
