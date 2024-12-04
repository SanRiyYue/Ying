package org.example.dao;


import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.List;

@Component
public abstract class AbstractDao<T> extends JdbcDaoSupport {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String table;
    private Class<T> entityClass;
    private RowMapper<T> rowMapper;

    public AbstractDao() {
        //获取当前类型的泛型类型:
        this.entityClass = getParameterizedType();                         //.User
        this.table = this.entityClass.getSimpleName().toLowerCase() + "s"; //users
        this.rowMapper = new BeanPropertyRowMapper<T>(entityClass);
    }
    @PostConstruct
    public void init() {
        super.setJdbcTemplate(jdbcTemplate);
        if (getJdbcTemplate() == null) {
            throw new RuntimeException("JdbcTemplate is null, please check it.");
        }
    }
    public T getById(long id) {
        return getJdbcTemplate().queryForObject("SELECT * FROM" + table + "WHERE id = ?", this.rowMapper, id);
    }

    public List<T> getAll(int pageIndex) {
        int limit = 100;
        int offset = (pageIndex - 1) * limit;
        return getJdbcTemplate().query("SELECT * FROM " + table + " LIMIT ? OFFSET ?", this.rowMapper, limit, offset);
    }

    public void deleteById(long id) {
        getJdbcTemplate().update("DELETE * FROM " + table + "WHERE id = ?", this.rowMapper, id);
    }

    public RowMapper<T> getRowMapper() {
        return this.rowMapper;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getParameterizedType() {
        Type type = getClass().getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Class " + getClass().getName() + " does not have parameterized Type.");
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (actualTypeArguments.length != 1) {
            throw new IllegalArgumentException("Class " + getClass().getName() + " has more than one parameter.");
        }
        Type r = actualTypeArguments[0];
        if (!(r instanceof Class<?>)) {
            throw new IllegalArgumentException("Class " + getClass().getName() + "does not have parameterized type of class.");
        }
        return (Class<T>) r;
    }
}
