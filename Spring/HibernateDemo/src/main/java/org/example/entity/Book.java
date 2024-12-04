package org.example.entity;

import jakarta.persistence.*;

@Entity
public class Book extends AbstractEntity {


    private String title;

    public Book() {
    }

    public Book(Long id, String title) {
        setId(id);
        setTitle(title);
    }

    @Column(nullable = false, length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
